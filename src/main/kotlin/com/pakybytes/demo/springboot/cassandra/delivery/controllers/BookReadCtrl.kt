package com.pakybytes.demo.springboot.cassandra.delivery.controllers

import com.pakybytes.demo.springboot.cassandra.core.services.BookService
import com.pakybytes.demo.springboot.cassandra.delivery.adapters.BookMapper
import com.pakybytes.demo.springboot.cassandra.delivery.adapters.ResponseAdapter
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("")
class BookReadCtrl(private val service: BookService,
                   private val bookMapper: BookMapper,
                   private val responseAdapter: ResponseAdapter) {

    private val log = LoggerFactory.getLogger(BookReadCtrl::class.java)


    /** curl -X GET 'http://localhost:8080/raw/book/count
     */
    @GetMapping("/raw/book/count", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun getCount(): ResponseEntity<String> {

        val result = service.countRaw()

        log.info("Finish retrieving book with $result")
        return responseAdapter.transformResponse(result)
    }


    /** curl -X GET 'http://localhost:8080/raw/book/title?id=137abbf0-54c2-11e9-870d-2f329f927c23'
     */
    @GetMapping("/orm/books", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun getBooksByQueryBuilder(): ResponseEntity<String> {

        val result = service.queryBuilder()

        log.info("Finish retrieving book with $result")
        return responseAdapter.transformResponse(result)
    }

    /** curl -X GET 'http://localhost:8080/raw/book/title?id=137abbf0-54c2-11e9-870d-2f329f927c23'
     */
    @GetMapping("/raw/book/title", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun getTitle(@RequestParam(required = true) id: String): ResponseEntity<String> {

        val model = bookMapper.map(id)

        val result = service.queryTitle(model)

        log.info("Finish retrieving book with $result")
        return responseAdapter.transformResponse(result)
    }

    /** curl -X GET 'http://localhost:8080/raw/book/title?id=137abbf0-54c2-11e9-870d-2f329f927c23'
     */
    @GetMapping("/raw/book", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun getBook(@RequestParam(required = true) id: String): ResponseEntity<String> {

        val model = bookMapper.map(id)

        val result = service.queryBook(model)

        log.info("Finish retrieving book with $result")
        return responseAdapter.transformResponse(listOf(result))
    }
}