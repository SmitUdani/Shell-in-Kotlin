fun main() {
    while (true) {
        print("$ ")
        val command = readln()
        when {
            command.startsWith("echo") -> println(command.substringAfter("echo "))
            command == "exit 0" -> break
            else -> println("$command: command not found")
        }
    }
}
