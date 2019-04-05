package com.pakybytes.demo.springboot.cassandra.delivery.adapters

import com.pakybytes.demo.springboot.cassandra.core.models.Book
import com.pakybytes.demo.springboot.cassandra.delivery.viewmodels.BookVM
import com.pakybytes.demo.springboot.cassandra.delivery.viewmodels.CreateBookVM
import com.pakybytes.demo.springboot.cassandra.delivery.viewmodels.DeleteBookVM
import com.pakybytes.demo.springboot.cassandra.delivery.viewmodels.UpdateBookVM
import org.springframework.stereotype.Component
import java.util.*

@Component
class BookMapper {

    /** Transform view model into core model */
    fun map(book: BookVM): Book? =
            when {
                book is CreateBookVM -> Book(book.title, book.publisher, book.tags)
                book is UpdateBookVM -> Book("", book.publisher, emptySet(), UUID.fromString(book.id))
                book is DeleteBookVM -> Book("", "", emptySet(), UUID.fromString(book.id))
                else -> null
            }

    /** Transform view model into core model */
    fun map(id: String): Book =
            Book("", "", emptySet(), UUID.fromString(id))

}