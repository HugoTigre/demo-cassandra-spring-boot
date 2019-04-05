package com.pakybytes.demo.springboot.cassandra

import com.pakybytes.demo.springboot.cassandra.conf.SpringBootCassandraDemoConfig
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication


@SpringBootApplication
@EnableConfigurationProperties(SpringBootCassandraDemoConfig::class)
class SpringBootCassandraDemoApplication {}

fun main(args: Array<String>) {

    runApplication<SpringBootCassandraDemoApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}

