package com.pakybytes.demo.springboot.cassandra.core.services.utils

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import java.io.IOException


@Service
class JsonUtils(private val mapper: ObjectMapper) {

    init {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
    }

    @Throws(IOException::class)
    fun toJson(instance: Any): String {
        return mapper.writeValueAsString(instance)
    }

    @Throws(IOException::class)
    fun <T> fromJson(json: String, clazz: Class<T>): T {
        return mapper.readValue(json, clazz)
    }
}