package examples.delegated_properties

import kotlin.properties.Delegates

class People {
    var name: String by Delegates.notNull();

    fun init(name: String) {
        this.name = name
    }
}


fun main(args: Array<String>) {
    val user = People();
    user.init("Carl")
    println(user.name)
}