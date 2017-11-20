package examples.task.solution002

fun joinOptions(options: Collection<String>): String {
    var sb = StringBuilder()
    sb.append("[")
    var it = options.iterator()
    while (it.hasNext()) {
        var str = it.next()
        sb.append(str)
        if (it.hasNext()) {
            sb.append(", ")
        }
    }
    sb.append("]")
    return sb.toString()
}