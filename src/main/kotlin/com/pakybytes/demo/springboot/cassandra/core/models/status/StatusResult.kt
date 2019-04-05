package com.pakybytes.demo.springboot.cassandra.core.models.status

import java.util.*

data class StatusResult(
        val id: Int,
        val status: String? = null,
        val timestamp: Date? = null,
        val path: String? = null,
        val version: String? = null,
        val appId: String? = null, // Identifies msg origin
        val token: String? = null,
        val resourceId: String? = null)


data class StatusResults(
        val statusResults: List<StatusResult>)