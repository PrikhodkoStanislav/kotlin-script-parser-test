package examples.problems.palindrome

fun main(args: Array<String>) {
    println(isPalindrome("Ili"))
}

fun isPalindrome(s: String): Boolean {
    var palindrome = StringBuilder(s).reverse().toString();
    return palindrome.equals(s, true)
}
