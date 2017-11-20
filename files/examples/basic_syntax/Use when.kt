package examples.basic_syntax

fun main(args: Array<String>) {
    case("Hello")
    case(1)
    case(System.currentTimeMillis())
    case(MyClass())
    case("hello")
}


class MyClass() {
}

fun case(obj: Any) {
    when (obj) {
        1 -> println("One")
        "Hello" -> println("Greeting")
        is Long -> println("Long")
        !is String -> println("Not a String")
        else -> println("Unknown")
    }
}
