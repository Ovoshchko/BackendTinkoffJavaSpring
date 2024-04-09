package edu.java.scrapper.service.bot;

import edu.java.scrapper.dto.request.LinkUpdate;
import edu.java.scrapper.kafka.KafkaIntegrationTest;
import java.net.URI;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScrapperQueueProducerIntegrationTest extends KafkaIntegrationTest {

    public static final URI URL = URI.create("http://example.com");
    public static final List<String> DESCRIPTION = List.of("description");
    public static final List<Long> TG_CHAT_IDS = List.of(1L);
    public static final LinkUpdate LINK_UPDATE = new LinkUpdate(1L, URL, DESCRIPTION, TG_CHAT_IDS);
    public static final String SUCCESS = "Success";
    public static final int PARTITIONS = 3;
    public static final short REPLICATION_FACTOR = (short) 1;
    public static final String TEST_GROUP = "testGroup";
    public static final String EARLIEST = "earliest";
    public static final String TRUSTED_PACKAGES = "*";
    private KafkaTemplate<String, LinkUpdate> kafkaTemplate;
    private KafkaConsumer<String, LinkUpdate> consumer;
    @Autowired
    private KafkaAdmin kafkaAdmin;

    @BeforeEach
    void start() {
        configTopic();
        consumer = new KafkaConsumer<>(kafkaConsumerFactory());
        consumer.subscribe(Collections.singleton(PROPERTIES.getTopic()));
        kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaProducerFactory()));
    }

    @Test
    public void testPostUpdate() {

        ScrapperQueueProducer scrapperQueueProducer = new ScrapperQueueProducer(kafkaTemplate, PROPERTIES);

        String result = scrapperQueueProducer.postUpdate(LINK_UPDATE);

        assertEquals(SUCCESS, result);

        ConsumerRecords<String, LinkUpdate> records = consumer.poll(Duration.ofSeconds(20));

        assertEquals(1, records.count());
        records.forEach(record -> {
            assertThat(record.value()).isEqualTo(LINK_UPDATE);
        });
    }

    private Map<String, Object> kafkaProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, PROPERTIES.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, PROPERTIES.getKeySerializer());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PROPERTIES.getValueSerializer());
        return props;
    }

    private void configTopic() {
        System.out.println(kafkaAdmin.getConfigurationProperties());
        AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
        NewTopic newTopic = new NewTopic(PROPERTIES.getTopic(), PARTITIONS, REPLICATION_FACTOR);
        adminClient.createTopics(Collections.singleton(newTopic));
    }

    private Map<String, Object> kafkaConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, PROPERTIES.getBootstrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, EARLIEST);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, TEST_GROUP);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, LinkUpdate.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, TRUSTED_PACKAGES);
        return props;
    }

}
