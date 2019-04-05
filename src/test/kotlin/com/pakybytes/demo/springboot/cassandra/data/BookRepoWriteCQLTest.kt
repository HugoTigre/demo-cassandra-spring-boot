package com.pakybytes.demo.springboot.cassandra.data

import common.IntegrationTest
import common.Mocks
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@IntegrationTest
internal class BookRepoWriteCQLTest(
        @Autowired val bookRepoWriteCQL: BookRepoWriteCQL) {

    @Test
    fun insertORM() {
        val book = bookRepoWriteCQL.insertORM(Mocks.book)
        assertEquals(book.title, Mocks.book.title)
        assertEquals(book.publisher, Mocks.book.publisher)
        assertEquals(book.tags, Mocks.book.tags)
    }

    @Test
    fun insertRaw() {
        val ok = bookRepoWriteCQL.insertRaw(Mocks.book)
        assertEquals(ok, true)
    }

    @Test
    fun insertRawPreparedStatement() {
        val ok = bookRepoWriteCQL.insertRawPreparedStatement(Mocks.book)
        assertTrue(ok)
    }

    @Test
    fun updateRaw() {
        val ok = bookRepoWriteCQL.updateRaw(Mocks.book)
        assertTrue(ok)
    }

    @Test
    fun deleteRaw() {
        val ok = bookRepoWriteCQL.deleteRaw(Mocks.book)
        assertTrue(ok)
    }

}