fun main() {
    while (true) {
        print("$ ")
        val command = readln()
        when {
            command.startsWith("echo") -> println(command.substringAfter("echo "))
            command.startsWith("type") -> handleTypeCommand(command)
            command == "exit 0" -> break
            else -> println("$command: command not found")
        }
    }
}

fun handleTypeCommand(command: String) {
    val subCommand = command.substringAfter("type ")
    val builtInCommands = setOf("type", "echo", "exit")

    if(subCommand !in builtInCommands)
        println("$subCommand: not found")

    else println("$subCommand is a shell builtin")
}
