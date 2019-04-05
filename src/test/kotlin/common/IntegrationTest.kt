package common

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ActiveProfiles("test") // Overrides configuration parameters
@Tag("integration")
@ExtendWith(SpringExtension::class, CassandraDockerContainerExtension::class)
@ContextConfiguration(initializers = [CassandraDockerContainerExtension.Initializer::class])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
annotation class IntegrationTest