fun smartSplit(input: String, separator: Char): String {
    return buildString {
        var flag = 0
        var i = 0

        while(i < input.length) {
            val char = input[i]

            if (char == separator) flag = 1 - flag
            else if (char == ' ' && flag == 0 && input[i - 1] == char) continue
            else if (char == '\\') append(input[++i])
            else append(char)

            i++
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