fun singleQuotes(input: String): String {
    return buildString {
        var flag = 0
        for (i in input.indices) {
            val char = input[i]

            if (char == '\'') flag = 1 - flag
            else if (char == ' ' && flag == 0 && input[i - 1] == char) continue
            else append(char)
        }
    }
}

fun doubleQuotes(input: String): String {
    return buildString {
        var flag = 0
        for (i in input.indices) {
            val char = input[i]

            if (char == '"') flag = 1 - flag
            else if (char == ' ' && flag == 0 && input[i - 1] == char) continue
            else append(char)
        }
    }
}