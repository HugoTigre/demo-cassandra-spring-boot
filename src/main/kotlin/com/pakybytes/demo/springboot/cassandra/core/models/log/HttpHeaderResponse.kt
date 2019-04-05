package com.pakybytes.demo.springboot.cassandra.core.models.log

data class HttpHeaderResponse(
        val status: Int?,
        val msg: String?,
        val header: Map<String, List<String>?>?
)