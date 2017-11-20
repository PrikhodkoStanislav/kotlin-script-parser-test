package examples.task.solution008

import java.util.*

fun getList(): List<Int> {
    val arrayList = arrayListOf(1, 5, 2)
    Collections.sort(arrayList, Comparator { t1, t2 -> t2 - t1})
    return arrayList
}