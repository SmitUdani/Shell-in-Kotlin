import java.io.File

fun main() {
    while (true) {
        print("$ ")

        val input = readln()

        if(input.startsWith("exit")) break

        handleInput(input)
    }
}

fun handleInput(input: String) {
    val command = input.substringBefore(" ")
    val arguments = input.substringAfter(" ")

    when(command) {
        "echo" -> println(arguments)
        "type" -> handleTypeCommand(arguments.trim())
        else -> handleOtherCommand(command, arguments)
    }
}

val builtInCommands = setOf("type", "echo", "exit")
val directories = System.getenv("PATH").split(File.pathSeparator)
val fileSeperator: String = File.separator


fun handleOtherCommand(command: String, argumets: String) {
    for(dir in directories) {
        if(File("$dir$fileSeperator$command").exists()) {
            val process = ProcessBuilder(command, * argumets.split(" ").toTypedArray()).inheritIO()
            process.start().waitFor()
            return
        }
    }

    println("$command: command not found")
}

fun handleTypeCommand(args: String) {

    if(args in builtInCommands) {
        println("$args is a shell builtin")
        return
    }

    for(dir in directories) {
        if(File("$dir$fileSeperator$args").exists()){
            println("$args is $dir$fileSeperator$args")
            return
        }
    }

    println("$args: not found")

}
