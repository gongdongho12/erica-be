package erica.be.query

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery

@DgsComponent
class BasicQueryResolver {
    @DgsQuery
    fun helloWorld(): String {
        return "Hello Wolrd!"
    }

    @DgsQuery
    fun plus(a: Int, b: Int): Int {
        return a + b
    }
}
