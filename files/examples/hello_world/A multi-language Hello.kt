package examples.hello_world

fun main(args: Array<String>) {
    val lang = if (args.size == 0) "EN" else args[0];
    println(when (lang) {
        "EN" -> "Hello!"
        "RU" -> "Привет!"
        else -> "Sorry. I can't greet you in $lang yet!"
    });
}
