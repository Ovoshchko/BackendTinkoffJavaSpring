package edu.java.scrapper.service.bot;

import edu.java.scrapper.configuration.kafka.KafkaProducerProperties;
import edu.java.scrapper.dto.request.LinkUpdate;
import lombok.Data;
import org.springframework.kafka.core.KafkaTemplate;

@Data
public class ScrapperQueueProducer implements BotService {

    public static final String SUCCESS = "Success";
    private final KafkaTemplate<String, LinkUpdate> kafkaTemplate;
    private final KafkaProducerProperties kafkaProducerProperties;

    @Override
    public String postUpdate(LinkUpdate linkUpdate) {
        kafkaTemplate.send(kafkaProducerProperties.getTopic(), linkUpdate);
        return SUCCESS;
    }
}
