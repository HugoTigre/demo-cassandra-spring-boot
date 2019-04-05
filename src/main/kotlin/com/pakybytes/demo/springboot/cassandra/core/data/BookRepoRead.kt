package com.pakybytes.demo.springboot.cassandra.core.data

import com.pakybytes.demo.springboot.cassandra.core.models.Book
import java.math.BigInteger

interface BookRepoRead {

    fun countRaw(): BigInteger

    fun queryBuilder(): List<Book>

    fun queryTitle(book: Book): String

    fun queryBook(book: Book): Book
}