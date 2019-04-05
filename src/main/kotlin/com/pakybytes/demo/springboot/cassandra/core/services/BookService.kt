package com.pakybytes.demo.springboot.cassandra.core.services

import com.datastax.driver.core.ResultSet
import com.pakybytes.demo.springboot.cassandra.core.data.BookRepoRead
import com.pakybytes.demo.springboot.cassandra.core.data.BookRepoWrite
import com.pakybytes.demo.springboot.cassandra.core.models.Book
import org.springframework.stereotype.Component
import java.math.BigInteger

@Component
class BookService(private val bookRepoWrite: BookRepoWrite,
                  private val bookRepoRead: BookRepoRead) {

    // Writes
    //

    fun addORM(book: Book): Book {
        return bookRepoWrite.insertORM(book)
    }

    fun addRaw(book: Book): Boolean {
        return bookRepoWrite.insertRaw(book)
    }

    fun addRawPreparedStatement(book: Book): Boolean {
        return bookRepoWrite.insertRawPreparedStatement(book)
    }


    fun updateRaw(book: Book): Boolean {
        return bookRepoWrite.updateRaw(book)
    }

    fun deleteRaw(book: Book): Boolean {
        return bookRepoWrite.deleteRaw(book)
    }

    // Reads
    //

    fun countRaw(): BigInteger {
        return bookRepoRead.countRaw()
    }

    fun queryBuilder(): List<Book> {
        return bookRepoRead.queryBuilder()
    }

    fun queryTitle(book: Book): String {
        return bookRepoRead.queryTitle(book)
    }

    fun queryBook(book: Book): Book {
        return bookRepoRead.queryBook(book)
    }
}