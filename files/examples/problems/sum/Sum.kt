package examples.problems.sum

fun sum(a: IntArray): Int {
    var sum = 0;
    for(n in a) {
        sum += n
    }

    return sum
}