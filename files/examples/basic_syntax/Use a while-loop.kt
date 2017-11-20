package examples.basic_syntax

fun main(args: Array<String>) {
    var i = 0
    while (i < args.size)
        println(args[i++])
}