package examples.basic_syntax

fun parseInt(str: String): Int? {
    try {
        return Integer.parseInt(str)
    } catch (e: NumberFormatException) {
        println("One of the argument isn't Int");
    }
    return null;
}

fun main(args: Array<String>) {
    if (args.size < 2) {
        println("No number supplide!")
    } else {
        val x = parseInt(args[0])
        val y = parseInt(args[1])

        if (x != null && y != null) {
            print(x * y)
        } else {
            println("One of the argument is null!")
        }
    }
}
