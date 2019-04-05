package common;

import com.datastax.driver.core.Session;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This starts a cassandra container for all tests.
 * Restarting the container for each class is not working properly with this container.
 */
public class CassandraDockerContainerExtension implements BeforeAllCallback, AfterAllCallback {

    private static Logger LOG = LoggerFactory.getLogger(CassandraDockerContainerExtension.class);

    private static CassandraContainer cassandraContainer =
            (CassandraContainer) new CassandraContainer("cassandra:latest")
                    .waitingFor(Wait.defaultWaitStrategy());

    private static Session session;

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
//        cassandraContainer.stop(); // cassandra container is not stopping properly after each class
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
//        cassandraContainer.start();
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public Initializer() {
        }


        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            cassandraContainer.start();

            TestPropertyValues.of(
                    "spring.data.cassandra.keyspaceName=essentials",
                    "spring.data.cassandra.contactPoints=localhost",
                    "spring.data.cassandra.port=" + cassandraContainer.getMappedPort(9042),
                    "spring.data.cassandra.schemaAction=CREATE_IF_NOT_EXISTS",
                    "spring.data.cassandra.username=cassandra",
                    "spring.data.cassandra.password=cassandra"
            ).applyTo(applicationContext.getEnvironment());

            session = cassandraContainer.getCluster().newSession();
            createKeyspaceIfNotExists();

            session = cassandraContainer.getCluster().connect("essentials");
            createSeedData();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> cassandraContainer.stop()));
        }

        /**
         * This is needed so that when the application is started in for integrations testing
         * it doesn't complain about the keyspace
         */
        void createKeyspaceIfNotExists() {
            session.execute(
                    "create keyspace IF NOT EXISTS essentials with replication = {'class':'SimpleStrategy', 'replication_factor':1};"
            );
        }

        void createSeedData() {
            try {
                String createTableIfNotExists = new String(
                        Files.readAllBytes(Paths.get(new ClassPathResource("cassandra/cassandra_create_table.cql").getFile().getAbsolutePath()
                        )));

                String insertSeedData = new String(
                        Files.readAllBytes(Paths.get(
                                new ClassPathResource("cassandra/cassandra_insert_book_data.cql").getFile().getAbsolutePath()
                        )));

                session.execute(createTableIfNotExists);
                session.execute(insertSeedData);

            } catch (IOException e) {
                LOG.error("Failed to access seed data file", e);
            }
        }
    }

}
