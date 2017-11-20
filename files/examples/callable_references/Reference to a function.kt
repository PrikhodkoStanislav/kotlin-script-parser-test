package examples.callable_references

fun main(args: Array<String>) {
    val numbers = listOf<Int>(1,2,3);
    println(numbers.filter(::isOdd))
}

fun isOdd(x: Int) = x % 2 != 0