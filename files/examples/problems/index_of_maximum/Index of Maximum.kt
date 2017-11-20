package examples.problems.index_of_maximum

fun indexOfMax(a: IntArray): Int? {
    var max = Integer.MIN_VALUE;

    for(i in a.indices) {
        if(a[i] > max) {
            max = a[i]
        }
    }
    return max
}

fun main(args: Array<String>) {
    var ar = intArrayOf(1,2,3,4,5,6,7,8,9)
    var max = indexOfMax(ar)
    println(max)
}