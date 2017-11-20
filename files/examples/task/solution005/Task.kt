package examples.task.solution005

class Person(var name: String, var age: Int) {
    override fun toString(): String {
        return "Person(name=${name}, age=${age})"
    }
}

fun getPeople(): List<Person> {
    return listOf(Person("Alice", 29), Person("Bob", 31))
}