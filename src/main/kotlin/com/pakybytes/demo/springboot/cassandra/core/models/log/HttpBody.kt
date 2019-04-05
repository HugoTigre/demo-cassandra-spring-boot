package com.pakybytes.demo.springboot.cassandra.core.models.log

data class HttpBody(
        val content: List<String>?,
        val size: Int?
)