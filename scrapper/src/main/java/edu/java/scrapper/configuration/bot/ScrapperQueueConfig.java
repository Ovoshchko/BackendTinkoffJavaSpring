package edu.java.scrapper.configuration.bot;

import edu.java.scrapper.configuration.kafka.KafkaProducerProperties;
import edu.java.scrapper.dto.request.LinkUpdate;
import edu.java.scrapper.service.bot.BotService;
import edu.java.scrapper.service.bot.ScrapperQueueProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class ScrapperQueueConfig {

    @Bean
    public BotService botService(KafkaTemplate<String, LinkUpdate> kafkaTemplate, KafkaProducerProperties properties) {
        return new ScrapperQueueProducer(kafkaTemplate, properties);
    }
}
