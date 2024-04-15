package edu.java.bot.service.kafka;

import edu.java.bot.configuration.kafka.KafkaProducerProperties;
import edu.java.bot.dto.request.LinkUpdate;
import edu.java.bot.service.update_processor.UpdateProcessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final UpdateProcessorService updateProcessorService;
    private final KafkaProducerProperties kafkaProducerProperties;
    private final KafkaTemplate<String, LinkUpdate> kafkaTemplate;

    @RetryableTopic(attempts = "1", autoCreateTopics = "false", kafkaTemplate = "kafkaTemplate")
    @KafkaListener(topics = "${kafka.consumer.topic}")
    public void listen(LinkUpdate linkUpdate) {
        updateProcessorService.postUpdate(linkUpdate);
    }
}
