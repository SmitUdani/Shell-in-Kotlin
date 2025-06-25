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
    val arguments = input.substringAfter(" ").trim()

    when(command) {
        "echo" -> echo(arguments)
        "type" -> handleTypeCommand(arguments)
        "pwd" -> println(System.getProperty("user.dir"))
        "cd" -> changeDirectory(command, arguments)
        "cat" -> cat(arguments)
        else -> handleOtherCommand(command, arguments)
    }
}

val builtInCommands = setOf("type", "echo", "exit", "pwd")
val directories = System.getenv("PATH").split(File.pathSeparator)
val fileSeperator: String = File.separator

fun cat(arguments: String) {
    val sep = if(arguments.startsWith("'")) "'" else "\""
    val files = arguments.split(sep).filter { it.isNotBlank() }
    print( files.joinToString(separator = "") { File(it).readText(Charsets.UTF_8) } )
}

fun echo(arguments: String) {
    if(arguments.startsWith("'"))
        println(singleQuotes(arguments))

    else println(doubleQuotes(arguments))
}

fun changeDirectory(command: String, arguments: String) {
    if(arguments == "~") {
        System.setProperty("user.dir", System.getenv("HOME"))
        return
    }

    val one = File(System.getProperty("user.dir") + fileSeperator + arguments)
    val two = File(arguments)

    if(one.isDirectory)
        System.setProperty("user.dir", one.canonicalPath)
    else if(two.isDirectory)
        System.setProperty("user.dir", two.canonicalPath)
    else println("$command: $arguments: No such file or directory")
}


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
