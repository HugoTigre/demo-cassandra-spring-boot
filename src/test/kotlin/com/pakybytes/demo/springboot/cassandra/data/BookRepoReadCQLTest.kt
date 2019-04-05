package com.pakybytes.demo.springboot.cassandra.data

import common.IntegrationTest
import common.Mocks
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigInteger


@IntegrationTest
internal class BookRepoReadCQLTest(
        @Autowired val bookRepoReadCQL: BookRepoReadCQL) {


    @Test
    fun countRaw() {
        val count = bookRepoReadCQL.countRaw()
        assertEquals(BigInteger.ONE, count)
    }

    @Test
    fun queryBuilder() {
        val books = bookRepoReadCQL.queryBuilder()
        assertEquals(listOf(Mocks.book), books)
    }

    @Test
    fun queryTitle() {
        val title = bookRepoReadCQL.queryTitle(Mocks.book)
        assertEquals(Mocks.book.title, title)
    }

    @Test
    fun queryBook() {
        val newBook = bookRepoReadCQL.queryBook(Mocks.book)
        assertEquals(Mocks.book, newBook)
    }
}