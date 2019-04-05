package com.pakybytes.demo.springboot.cassandra.ioc

import com.pakybytes.demo.springboot.cassandra.conf.SpringBootCassandraDemoConfig
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
object ServiceLocator {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Autowired
    private lateinit var conf: SpringBootCassandraDemoConfig

    init {
        log.warn("Starting Service Locator [$this]...")
    }
}