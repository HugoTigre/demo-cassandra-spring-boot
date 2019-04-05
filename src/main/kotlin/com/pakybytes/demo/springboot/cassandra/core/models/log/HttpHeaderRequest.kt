package com.pakybytes.demo.springboot.cassandra.core.models.log

data class HttpHeaderRequest(
        val remoteAddress: String?,
        val method: String?,
        val uri: String?,
        val queryString: String?,
        val header: Map<String, List<String>?>?
)