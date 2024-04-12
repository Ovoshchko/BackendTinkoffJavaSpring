package edu.java.bot.service.kafka;

import edu.java.bot.configuration.kafka.KafkaConsumerProperties;
import edu.java.bot.configuration.kafka.KafkaProducerProperties;
import edu.java.bot.dto.request.LinkUpdate;
import edu.java.bot.kafka.KafkaIntegrationTest;
import edu.java.bot.service.update_processor.AllUpdateProcessorService;
import edu.java.bot.service.update_processor.UpdateProcessorService;
import edu.java.bot.utils.LinkUpdateDeserializer;
import java.net.URI;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class KafkaServiceKafkaTest extends KafkaIntegrationTest {
    public static final LinkUpdate LINK_UPDATE_VALID =
        new LinkUpdate(1L, URI.create("http://example.com"), List.of("description"), List.of(1L));
    public static final LinkUpdate LINK_UPDATE_INVALID =
        new LinkUpdate(1L, URI.create("http://example.com"), null, null);
    public static final String DLT_PREFIX = "-dlt";
    public static final String EARLIEST = "earliest";
    @Autowired
    private KafkaConsumerProperties kafkaConsumerProperties;
    @Autowired
    private KafkaProducerProperties kafkaProducerProperties;
    @Autowired
    private KafkaTemplate<String, LinkUpdate> kafkaTemplate;
    @Autowired
    private KafkaService kafkaService;
    @Autowired
    private UpdateProcessorService updateProcessorService;

    @Test
    void testListen_Success() throws InterruptedException {

        UpdateProcessorService updateProcessorServiceMock = mock(AllUpdateProcessorService.class);
        ReflectionTestUtils.setField(kafkaService, "updateProcessorService", updateProcessorServiceMock);

        kafkaTemplate.send(
            kafkaConsumerProperties.getTopic(),
            LINK_UPDATE_VALID
        );

        Thread.sleep(3000);

        verify(updateProcessorServiceMock, times(1)).postUpdate(any());
    }

    @Test
    void testListen_Invalid() throws InterruptedException {

        kafkaTemplate.send(
            kafkaConsumerProperties.getTopic(),
            LINK_UPDATE_INVALID
        );

        Consumer<String, LinkUpdate> consumer = new KafkaConsumer<>(consumerProps());
        consumer.subscribe(Collections.singleton(kafkaConsumerProperties.getTopic() + DLT_PREFIX));

        ConsumerRecords<String, LinkUpdate> records = consumer.poll(Duration.ofMillis(10000));

        assertEquals(1, records.count());
    }

    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LinkUpdateDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, EARLIEST);
        return props;
    }

    @TestConfiguration
    @RequiredArgsConstructor
    public static class TestConfig {

        private final KafkaConsumerProperties kafkaConsumerProperties;
        private final KafkaProducerProperties kafkaProducerProperties;

        @Bean
        public ConsumerFactory<String, LinkUpdate> consumerFactory() {
            return new DefaultKafkaConsumerFactory<>(consumerConfigs());
        }

        @Bean
        public ConcurrentKafkaListenerContainerFactory<String, LinkUpdate> kafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<String, LinkUpdate> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());
            return factory;
        }

        @Bean
        public KafkaTemplate<String, LinkUpdate> kafkaTemplate() {
            return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerFactory()));
        }

        private Map<String, Object> consumerConfigs() {
            Map<String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaConsumerProperties.getKeyDeserializer());
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LinkUpdateDeserializer.class);
            props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerProperties.getGroupId());
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerProperties.getAutoOffsetReset());
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaConsumerProperties.isEnableAutoCommit());
            props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, kafkaConsumerProperties.getAutoCommitIntervalMs());
            return props;
        }

        private Map<String, Object> producerFactory() {
            Map<String, Object> props = new HashMap<>();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProducerProperties.getKeySerializer());
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProducerProperties.getValueSerializer());
            return props;
        }
    }
}
