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
        else -> println("$command: command not found")
    }
}

val builtInCommands = setOf("type", "echo", "exit")

fun handleTypeCommand(args: String) {

    if(args in builtInCommands) {
        println("$args is a shell builtin")
        return
    }

    val paths = System.getenv("PATH").split(":")

    for(path in paths) {
        if(File("$path/$args").exists()){
            println("$args is $path/$args")
            return
        }
    }

    println("$args: not found")

}
