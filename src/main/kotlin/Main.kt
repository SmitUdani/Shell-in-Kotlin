fun main() {
    while (true) {
        print("$ ")

        val input = readln()

        if(input.startsWith("exit")) break

        handleInput(input)
    }
}

fun handleInput(input: String) {
    val inputList = parseInput(input)
    val command = inputList[0]
    val arguments = inputList.subList(1, inputList.size)

    when(command) {
        "echo" -> echo(arguments)
        "type" -> type(arguments)
        "pwd" -> pwd()
        "cd" -> cd(arguments)
        "cat" -> cat(arguments)
        else -> other(inputList)
    }
}
