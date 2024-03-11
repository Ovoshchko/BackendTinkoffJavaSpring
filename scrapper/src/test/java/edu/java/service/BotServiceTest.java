package edu.java.service;

import edu.java.clients.bot.BotWebClient;
import edu.java.dto.request.LinkUpdate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BotServiceTest {

    @Mock
    BotWebClient botWebClient;

    @InjectMocks
    BotService botService;

    @Test
    void postUpdate_ReturnsResponseFromWebClient() {
        long id = 1L;
        String url = "https://example.com";
        String description = "Test";
        List<Long> tgChatIds = List.of(1L, 2L);
        String expectedResponse = "Success";

        when(botWebClient.postUpdate(any(LinkUpdate.class))).thenReturn(expectedResponse);

        String response = botService.postUpdate(id, url, description, tgChatIds);

        assertEquals(expectedResponse, response);
        verify(botWebClient, times(1)).postUpdate(any(LinkUpdate.class));
    }
}

