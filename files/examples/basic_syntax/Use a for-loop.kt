package examples.basic_syntax

fun main(args: Array<String>) {
    for (arg in args) {
        println(arg)
    }

    // or

    for (i in args.indices)
        println(args[i])
}
