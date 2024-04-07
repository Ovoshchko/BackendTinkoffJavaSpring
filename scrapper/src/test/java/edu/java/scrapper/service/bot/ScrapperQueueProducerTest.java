package edu.java.scrapper.service.bot;

import edu.java.scrapper.configuration.kafka.KafkaProducerProperties;
import edu.java.scrapper.dto.request.LinkUpdate;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScrapperQueueProducerTest {

    public static final String TEST_TOPIC = "testTopic";
    public static final LinkUpdate LINK_UPDATE = new LinkUpdate(1L, null, null, null);
    public static final String SUCCESS = "Success";

    @Mock
    private KafkaProducerProperties kafkaProperties;

    @Mock
    private KafkaTemplate<String, LinkUpdate> kafkaTemplate;
    @InjectMocks
    private ScrapperQueueProducer producer;


    @BeforeEach
    void setUp() {
        when(kafkaProperties.getTopic()).thenReturn(TEST_TOPIC);
        when(kafkaTemplate.send(eq(kafkaProperties.getTopic()), eq(LINK_UPDATE))).thenReturn(null);
    }

    @Test
    void postUpdate_Success() {

        String result = producer.postUpdate(LINK_UPDATE);

        assertEquals(SUCCESS, result);
        verify(kafkaTemplate, times(1)).send(TEST_TOPIC, LINK_UPDATE);
    }
}
