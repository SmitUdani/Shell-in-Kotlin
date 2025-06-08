fun main() {
    while (true) {
        print("$ ")
        when(val command = readln()){
            "exit 0" -> break
            else -> println("$command: command not found")
        }
    }
}
