package com.pakybytes.demo.springboot.cassandra.ioc

import com.pakybytes.demo.springboot.cassandra.data.BookRepoReadCQL
import com.pakybytes.demo.springboot.cassandra.data.BookRepoWriteCQL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.cassandra.core.CassandraOperations

@Configuration
class ReposConfig {

    @Autowired
    lateinit var template: CassandraOperations

    @Autowired
    lateinit var session: com.datastax.driver.core.Session

    @Autowired
    lateinit var env: Environment

    @Bean
    fun bookRepoWrite(): BookRepoWriteCQL =
            BookRepoWriteCQL(template, session)

    @Bean
    fun bookRepoRead(): BookRepoReadCQL =
            BookRepoReadCQL(template, session, env)
}