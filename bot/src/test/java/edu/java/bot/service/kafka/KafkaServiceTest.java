package edu.java.bot.service.kafka;

import edu.java.bot.configuration.kafka.KafkaProducerProperties;
import edu.java.bot.dto.request.LinkUpdate;
import edu.java.bot.service.update_processor.UpdateProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import java.util.List;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaServiceTest {

    public static final String LINK_UPDATE_DLQ = "linkUpdate_dlq";
    public static final String ERROR = "Error";
    public static final String URL = "http://example.com";
    public static final String DESCRIPTION = "Description";
    private static final LinkUpdate linkUpdate = new LinkUpdate(1L, URL, List.of(DESCRIPTION), List.of(1L));
    @Mock
    private UpdateProcessorService updateProcessorService;
    @Mock
    private KafkaProducerProperties kafkaProducerProperties;
    @Mock
    private KafkaTemplate<String, LinkUpdate> kafkaTemplate;
    @InjectMocks
    private KafkaService kafkaService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void listen_Success() {
        doNothing().when(updateProcessorService).postUpdate(linkUpdate);
        kafkaService.listen(linkUpdate);
        verify(updateProcessorService, times(1)).postUpdate(linkUpdate);
        verify(kafkaTemplate, never()).send(anyString(), any(LinkUpdate.class));
    }

    @Test
    void listen_Exception() {
        doThrow(new RuntimeException(ERROR)).when(updateProcessorService).postUpdate(linkUpdate);
        when(kafkaProducerProperties.getTopicDlq()).thenReturn(LINK_UPDATE_DLQ);
        kafkaService.listen(linkUpdate);
        verify(updateProcessorService, times(1)).postUpdate(linkUpdate);
        verify(kafkaTemplate, times(1)).send(anyString(), eq(linkUpdate));
    }
}
