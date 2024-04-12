package edu.java.scrapper.kafka;

import edu.java.scrapper.configuration.kafka.KafkaProducerProperties;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@DirtiesContext
public abstract class KafkaIntegrationTest {

    public static KafkaContainer kafkaContainer =
        new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.2.1"));
    public static final String TEST_TOPIC = "linkUpdate";
    public final static KafkaProducerProperties PROPERTIES = new KafkaProducerProperties();

    static {
        kafkaContainer.start();
        PROPERTIES.setBootstrapServers(kafkaContainer.getBootstrapServers());
        PROPERTIES.setKeySerializer("org.apache.kafka.common.serialization.StringSerializer");
        PROPERTIES.setValueSerializer("org.springframework.kafka.support.serializer.JsonSerializer");
        PROPERTIES.setType("edu.java.scrapper.dto.request.LinkUpdate");
        PROPERTIES.setTopic(TEST_TOPIC);
        PROPERTIES.setPartitions(3);
        PROPERTIES.setReplicationFactor(1);
    }

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", PROPERTIES::getBootstrapServers);
        registry.add("spring.kafka.producer.bootstrap-servers", PROPERTIES::getBootstrapServers);
        registry.add("spring.kafka.consumer.bootstrap-servers", PROPERTIES::getBootstrapServers);
        registry.add(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, PROPERTIES::getBootstrapServers);
    }

}
