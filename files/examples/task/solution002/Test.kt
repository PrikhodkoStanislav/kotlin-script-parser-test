package examples.task.solution002

import org.junit.Test
import org.junit.Assert


class TestNamedArguments() {

    @Test fun testJoinToString() {
        Assert.assertEquals("[yes, no, may be]", joinOptions(listOf("yes", "no", "may be")))
    }

}