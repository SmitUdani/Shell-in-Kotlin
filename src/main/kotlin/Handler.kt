import java.io.File

fun echo(arguments: List<String>) {
    println(arguments.joinToString(" "))
}

fun pwd() {
    println(System.getProperty("user.dir"))
}

fun cat(files: List<String>) {
    print( files.joinToString(separator = "") { File(it).readText(Charsets.UTF_8) } )
}

fun cd(arguments: List<String>) {
    if(arguments[0] == "~") {
        System.setProperty("user.dir", System.getenv("HOME"))
        return
    }

    val one = File(System.getProperty("user.dir") + File.separator + arguments[0])
    val two = File(arguments[0])

    if(one.isDirectory)
        System.setProperty("user.dir", one.canonicalPath)

    else if(two.isDirectory)
        System.setProperty("user.dir", two.canonicalPath)

    else println("cd: ${arguments[0]}: No such file or directory")
}

fun other(inputList: List<String>) {
    val exePath = executables.find { it.endsWith(inputList[0]) }

    if(exePath == null) println("${inputList[0]}: command not found")

    else print(runCommand(inputList, exePath.parentFile))
}

fun type(arguments: List<String>) {
    val typeCommand = arguments[0]

    if(typeCommand in builtInCommands) {
        println("$typeCommand is a shell builtin")
        return
    }

    val exeCommand = executables.find { it.endsWith(typeCommand) }

    if(exeCommand == null) println("$typeCommand: not found")

    else println("$typeCommand is $exeCommand")

}