import java.io.File
import java.util.concurrent.TimeUnit

val builtInCommands = setOf("type", "echo", "exit", "pwd", "cd")
val executables = System.getenv("PATH").split(File.pathSeparator).flatMap {
    File(it).listFiles()?.filter { file -> file.canExecute() } ?: emptyList()
}

fun parseInput(input: String): List<String> {
    val res = mutableListOf<String>()
    val curr = StringBuilder()
    var i = 0; val n = input.length
    var singleQuote = false
    var doubleQuote = false

    while (i < n) {
        val char = input[i]
        val next = input.getOrNull(i + 1)

        when {
            char == '\'' && !doubleQuote -> singleQuote = !singleQuote
            char == '\"' && !singleQuote -> doubleQuote = !doubleQuote

            char == '\\' && doubleQuote && (next == '\\' || next == '\"' || next == '$') -> {
                curr.append(next)
                i++
            }

            char == '\\' && !doubleQuote && !singleQuote -> {
                curr.append(next)
                i++
            }

            char == ' ' && !singleQuote && !doubleQuote -> {
                if(curr.isNotEmpty()) {
                    res.add(curr.toString())
                    curr.clear()
                }
            }

            else -> curr.append(char)
        }

        i++
    }

    if(curr.isNotEmpty()) res.add(curr.toString())

    return res.toList()
}

fun runCommand(parts: List<String>, workingDir: File): String {
    return try {
        val process = ProcessBuilder(*parts.toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()

        val completed = process.waitFor(60, TimeUnit.MINUTES)

        if (!completed) throw RuntimeException("Process timed out")

        val output = process.inputStream.bufferedReader().use { it.readText() }
        val error = process.errorStream.bufferedReader().use { it.readText() }

        if (process.exitValue() != 0) {
            throw RuntimeException("Command failed: $error")
        }

        output.trim()
    } catch (e: Exception) {
        "Error running command: ${e.message}"
    }
}
