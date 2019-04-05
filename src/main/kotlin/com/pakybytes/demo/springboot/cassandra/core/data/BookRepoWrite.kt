package com.pakybytes.demo.springboot.cassandra.core.data

import com.pakybytes.demo.springboot.cassandra.core.models.Book

interface BookRepoWrite {

    fun insertORM(book: Book): Book

    fun insertRaw(book: Book): Boolean

    fun insertRawPreparedStatement(book: Book): Boolean

    fun updateRaw(book: Book): Boolean

    fun deleteRaw(book: Book): Boolean
}