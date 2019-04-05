package com.pakybytes.demo.springboot.cassandra.data

import com.datastax.driver.core.Row
import com.datastax.driver.core.querybuilder.QueryBuilder
import com.pakybytes.demo.springboot.cassandra.core.data.BookRepoRead
import com.pakybytes.demo.springboot.cassandra.core.models.Book
import com.pakybytes.demo.springboot.cassandra.data.BookRepoRaw.COUNT_BOOK
import com.pakybytes.demo.springboot.cassandra.data.BookRepoRaw.QUERY_BOOK_BY_ID
import com.pakybytes.demo.springboot.cassandra.data.BookRepoRaw.QUERY_BOOK_TITLE_BY_ID
import com.pakybytes.demo.springboot.cassandra.data.BookRepoRaw.TABLE
import org.springframework.core.env.Environment
import org.springframework.data.cassandra.core.CassandraOperations
import org.springframework.data.cassandra.core.cql.RowMapper
import java.math.BigInteger


class BookRepoReadCQL(
        val template: CassandraOperations,
        val session: com.datastax.driver.core.Session,
        val env: Environment)
    : BookRepoRead {

    private val keySpace = env.getRequiredProperty("spring.data.cassandra.keyspaceName")

    override
    fun countRaw(): BigInteger {
        return template.cqlOperations.queryForObject(COUNT_BOOK, BigInteger::class.java)
    }

    override
    fun queryBuilder(): List<Book> {
        val rs = session.execute(
                QueryBuilder.select().all().from(keySpace, TABLE))

        return rs.map { row ->
            Book(
                    row.getString("title"),
                    row.getString("publisher"),
                    row.getSet("tags", String::class.java),
                    row.getUUID("id")
            )
        }
    }

    override
    fun queryTitle(book: Book): String {
        return template.cqlOperations.queryForObject(QUERY_BOOK_TITLE_BY_ID, String::class.java, book.id)
    }

    override
    fun queryBook(book: Book): Book {
        return template.cqlOperations.queryForObject(
                QUERY_BOOK_BY_ID,
                RowMapper<Book> { row: Row, i: Int ->
                    Book(
                            row.getString("title"),
                            row.getString("publisher"),
                            row.getSet("tags", String::class.java),
                            book.id
                    )
                },
                book.id)
    }

}