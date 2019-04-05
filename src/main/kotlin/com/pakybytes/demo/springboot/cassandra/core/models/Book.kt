package com.pakybytes.demo.springboot.cassandra.core.models

data class Book(
        val title: String,
        val publisher: String,
        val tags: Set<String>,
        val id: java.util.UUID? = null
)