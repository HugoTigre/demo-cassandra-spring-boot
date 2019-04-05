package com.pakybytes.demo.springboot.cassandra

import com.pakybytes.demo.springboot.cassandra.conf.SpringBootCassandraDemoConfig
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ApplicationContext
import org.springframework.core.Ordered
import org.springframework.stereotype.Component


@Component
class Startup : CommandLineRunner, Ordered {

    @Autowired
    private lateinit var conf: SpringBootCassandraDemoConfig

    @Autowired
    private lateinit var ctx: ApplicationContext

    private val log = LoggerFactory.getLogger(com.pakybytes.demo.springboot.cassandra.Startup::class.java)

    override
    fun getOrder() = 1

    override
    fun run(vararg args: String?) {
        log.debug("App name: ${conf.app.name}")
        log.debug("Using environment: ${conf.env}")
    }
}