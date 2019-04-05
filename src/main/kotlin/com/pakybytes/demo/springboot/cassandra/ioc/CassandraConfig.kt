package com.pakybytes.demo.springboot.cassandra.ioc

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean
import org.springframework.data.cassandra.config.SchemaAction
import org.springframework.data.cassandra.core.convert.CassandraConverter
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.core.io.ClassPathResource
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


@Configuration
class CassandraConfig : AbstractCassandraConfiguration() {

    @Autowired
    private lateinit var env: Environment

    override fun getKeyspaceName() =
            env.getProperty("spring.data.cassandra.keyspaceName", String::class.java)!!

    override fun getContactPoints() =
            env.getProperty("spring.data.cassandra.contactPoints", String::class.java)!!

    override fun getPort() =
            env.getProperty("spring.data.cassandra.port", Int::class.java)!!

    override fun getSchemaAction(): SchemaAction {
        return SchemaAction.CREATE_IF_NOT_EXISTS
    }

    override fun getKeyspaceCreations(): List<CreateKeyspaceSpecification> {
        val specification = CreateKeyspaceSpecification
                .createKeyspace(getKeyspaceName()).ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true)
                .withSimpleReplication()
        return Arrays.asList(specification)
    }

    @Bean
    override fun cluster(): CassandraClusterFactoryBean {
        val cluster = CassandraClusterFactoryBean()
        cluster.setContactPoints(getContactPoints())
        cluster.setPort(getPort())
        cluster.setJmxReportingEnabled(false)
        cluster.keyspaceCreations = getKeyspaceCreations()

        return cluster
    }

    @Bean
    @Throws(ClassNotFoundException::class)
    override fun cassandraMapping(): CassandraMappingContext {
        return CassandraMappingContext()
    }

    @Bean
    fun converter(): CassandraConverter {
        return MappingCassandraConverter(cassandraMapping())
    }

    /** Factory bean that creates the com.datastax.driver.core.Session instance */
    @Bean
    @Throws(Exception::class)
    override fun session(): CassandraSessionFactoryBean {
        val session = CassandraSessionFactoryBean()
        session.setCluster(cluster().getObject())
        session.setKeyspaceName(keyspaceName)
        session.setConverter(converter())
        session.schemaAction = getSchemaAction()
        session.setStartupScripts(getStartupScripts())

        return session
    }

    override fun getStartupScripts(): List<String> {
        val script = File(ClassPathResource("/cassandra/init.cql").file.absolutePath).readText()
        return script.split(";").filterNot(String::isBlank)
    }
}