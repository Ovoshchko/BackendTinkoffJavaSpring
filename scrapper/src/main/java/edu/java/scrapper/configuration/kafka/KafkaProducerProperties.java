package edu.java.scrapper.configuration.kafka;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka.producer", ignoreUnknownFields = false)
@Data
@NoArgsConstructor
public class KafkaProducerProperties {

    private String bootstrapServers;
    private String keySerializer;
    private String valueSerializer;
    private String type;
    private String topic;
    private int partitions;
    private int replicationFactor;
}
