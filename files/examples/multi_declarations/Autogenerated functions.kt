package examples.multi_declarations

fun main(args: Array<String>) {
    val user = User("Alex", 1)
    println(user)

    val secondUser = User("Alex", 1)
    val thirdUser = User("Max", 2)

    println("user == second: ${user == secondUser}")
    println("user == third: ${user == thirdUser}")

    println(user.copy())
    println(user.copy("Max"))
    println(user.copy(id = 2))
    println(user.copy("Max", 2))
}
