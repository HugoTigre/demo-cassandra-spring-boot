package common

import com.pakybytes.demo.springboot.cassandra.core.models.Book
import java.util.*

object Mocks {

    val book = Book(
            "Attack of the 90's",
            "your local bookstore",
            setOf("Action", "Fiction"),
            UUID.fromString("10969c50-56c6-11e9-a70a-19b9260fb40b")
    )
}