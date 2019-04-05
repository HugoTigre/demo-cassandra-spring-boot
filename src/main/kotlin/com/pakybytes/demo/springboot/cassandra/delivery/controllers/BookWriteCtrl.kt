package com.pakybytes.demo.springboot.cassandra.delivery.controllers

import com.pakybytes.demo.springboot.cassandra.core.services.BookService
import com.pakybytes.demo.springboot.cassandra.delivery.adapters.BookMapper
import com.pakybytes.demo.springboot.cassandra.delivery.adapters.ResponseAdapter
import com.pakybytes.demo.springboot.cassandra.delivery.viewmodels.CreateBookVM
import com.pakybytes.demo.springboot.cassandra.delivery.viewmodels.DeleteBookVM
import com.pakybytes.demo.springboot.cassandra.delivery.viewmodels.UpdateBookVM
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("")
class BookWriteCtrl(private val service: BookService,
                    private val bookMapper: BookMapper,
                    private val responseAdapter: ResponseAdapter) {

    private val log = LoggerFactory.getLogger(BookWriteCtrl::class.java)


    /** curl -d '{"title":"My Big Book", "publisher":"Black Books", "tags":["Action","Thriller"]}' -H "Content-Type: application/json" -X POST http://localhost:8080/raw/boo
     */
    @PostMapping("/orm/book", consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun addORM(@RequestBody(required = true) book: CreateBookVM): ResponseEntity<String> {

        val model = bookMapper.map(book) ?: return responseAdapter.badRequest()

        val result = service.addORM(model)

        log.info("Finish adding new book with $result")
        return responseAdapter.transformResponse(listOf(result))
    }

    /** curl -d '{"title":"My Big Book", "publisher":"Black Books", "tags":["Action","Thriller"]}' -H "Content-Type: application/json" -X POST http://localhost:8080/raw/boo
     */
    @PostMapping("/raw/book", consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun addRaw(@RequestBody(required = true) book: CreateBookVM): ResponseEntity<String> {

        val model = bookMapper.map(book) ?: return responseAdapter.badRequest()

        val result = service.addRaw(model)

        log.info("Finish adding new book with $result")
        return responseAdapter.transformResponse(result)
    }

    /** curl -d '{"title":"My Big Book", "publisher":"Black Books", "tags":["Action","Thriller"]}' -H "Content-Type: application/json" -X POST http://localhost:8080/preparestatement/book
     */
    @PostMapping("/preparestatement/book", consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun addRawPrepareStatement(@RequestBody(required = true) book: CreateBookVM): ResponseEntity<String> {

        val model = bookMapper.map(book) ?: return responseAdapter.badRequest()

        val result = service.addRawPreparedStatement(model)

        log.info("Finish adding new book with $result")
        return responseAdapter.transformResponse(result)
    }

    /** curl -d '{"id":"816c11d0-54c3-11e9-99b9-e38af1616a8d", "publisher":"Black Books Updated"}' -H "Content-Type: application/json" -X PUT http://localhost:8080/raw/book
     */
    @PutMapping("/raw/book", consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun updateRaw(@RequestBody(required = true) book: UpdateBookVM): ResponseEntity<String> {

        val model = bookMapper.map(book) ?: return responseAdapter.badRequest()

        val result = service.updateRaw(model)

        log.info("Finish adding new book with $result")
        return responseAdapter.transformResponse(result)
    }

    /** curl -d '{"id":"816c11d0-54c3-11e9-99b9-e38af1616a8d"}' -H "Content-Type: application/json" -X DELETE http://localhost:8080/raw/book
     */
    @DeleteMapping("/raw/book", consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun deleteRaw(@RequestBody(required = true) book: DeleteBookVM): ResponseEntity<String> {

        val model = bookMapper.map(book) ?: return responseAdapter.badRequest()

        val result = service.deleteRaw(model)

        log.info("Finish adding new book with $result")
        return responseAdapter.transformResponse(result)
    }
}