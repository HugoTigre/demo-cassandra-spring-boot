package com.pakybytes.demo.springboot.cassandra.data

import com.datastax.driver.core.PreparedStatement
import com.datastax.driver.core.Statement
import com.datastax.driver.core.utils.UUIDs
import com.pakybytes.demo.springboot.cassandra.core.data.BookRepoWrite
import com.pakybytes.demo.springboot.cassandra.core.models.Book
import com.pakybytes.demo.springboot.cassandra.data.BookRepoRaw.DELETE_BOOK_BY_ID
import com.pakybytes.demo.springboot.cassandra.data.BookRepoRaw.INSERT_BOOK
import com.pakybytes.demo.springboot.cassandra.data.BookRepoRaw.UPDATE_BOOK_PUBLISHER_BY_ID
import org.springframework.data.cassandra.core.CassandraOperations


class BookRepoWriteCQL(
        val template: CassandraOperations,
        val session: com.datastax.driver.core.Session)
    : BookRepoWrite {

    override
    fun insertORM(book: Book): Book {
        val model = Book(book.title, book.publisher, book.tags, UUIDs.timeBased())
        return template.insert(model) // can pass a list of books
    }

    override
    fun insertRaw(book: Book): Boolean {
        return template.cqlOperations.execute(INSERT_BOOK, UUIDs.timeBased(), book.title, book.publisher, book.tags)
    }

    override
    fun insertRawPreparedStatement(book: Book): Boolean {
        val insertStm: PreparedStatement = session.prepare(INSERT_BOOK)
        val bs = insertStm.bind(UUIDs.timeBased(), book.title, book.publisher, book.tags)
        return session.execute(bs as Statement).wasApplied()
    }

    override
    fun updateRaw(book: Book): Boolean {
        return template.cqlOperations.execute(UPDATE_BOOK_PUBLISHER_BY_ID, book.publisher, book.id)
    }

    override
    fun deleteRaw(book: Book): Boolean {
        return template.cqlOperations.execute(DELETE_BOOK_BY_ID, book.id)
    }

}