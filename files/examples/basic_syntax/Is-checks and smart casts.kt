package examples.basic_syntax

fun main(args: Array<String>) {
    println(getStringLength("Hi!"))
    println(getStringLength(5))
}

fun getStringLength(obj: Any): Int? {
    if (obj is String) {
        return obj.length
    }
    return null;
}

