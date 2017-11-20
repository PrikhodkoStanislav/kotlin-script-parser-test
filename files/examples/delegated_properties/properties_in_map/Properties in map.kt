package examples.delegated_properties.properties_in_map

class User(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int     by map
}

fun main(args: Array<String>) {
    val user = User(mapOf(
            "name" to "John Doe",
            "age" to 25
    ))

    println("names = ${user.name}, age = ${user.age}")
}
