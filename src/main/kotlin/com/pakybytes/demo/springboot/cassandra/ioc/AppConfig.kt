package com.pakybytes.demo.springboot.cassandra.ioc

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(ReposConfig::class)
class AppConfig {

}