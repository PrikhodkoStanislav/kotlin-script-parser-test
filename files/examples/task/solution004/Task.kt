package examples.task.solution004

fun containsEven(collection: Collection<Int>): Boolean {
    var it = collection.iterator()
    while (it.hasNext()) {
        if (it.next() % 2 == 0) {
            return true
        }
    }
    return false
}

