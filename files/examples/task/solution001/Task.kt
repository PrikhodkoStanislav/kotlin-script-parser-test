package examples.task.solution001

fun toJSON(collection: Collection<Int>): String {
    var sb = StringBuilder()
    sb.append("[")
    var it = collection.iterator()
    while (it.hasNext()) {
        var n = it.next()
        sb.append(n)
        if (it.hasNext()) {
            sb.append(", ")
        }
    }
    sb.append("]")
    return sb.toString()
}