package com.pakybytes.demo.springboot.cassandra.data

object BookRepoRaw {

    const val TABLE = "book"

    const val INSERT_BOOK = "insert into $TABLE (id, title, publisher, tags) values (?, ?, ?, ?)"

    const val UPDATE_BOOK_PUBLISHER_BY_ID = "UPDATE $TABLE SET publisher = ? WHERE id = ?"

    const val DELETE_BOOK_BY_ID = "DELETE FROM $TABLE WHERE id = ?"

    const val COUNT_BOOK = "SELECT COUNT(*) FROM $TABLE"

    const val QUERY_BOOK_TITLE_BY_ID = "SELECT title FROM $TABLE WHERE id = ?"

    const val QUERY_BOOK_BY_ID = "SELECT title, publisher, tags FROM $TABLE WHERE id = ?"
}