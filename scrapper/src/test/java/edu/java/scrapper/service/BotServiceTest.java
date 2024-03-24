package edu.java.scrapper.service;

import edu.java.scrapper.clients.bot.BotWebClient;
import edu.java.scrapper.dto.request.LinkUpdate;
import java.net.URI;
import java.util.List;
import edu.java.scrapper.service.bot.BotService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BotServiceTest {

    public static final long TG_CHAT_ID = 1L;
    public static final URI URL = URI.create("https://example.com");
    public static final String DESCRIPTION = "Test";
    public static final List<Long> TG_CHAT_IDS = List.of(1L, 2L);
    public static final String EXPECTED_ANSWER = "Success";
    @Mock
    BotWebClient botWebClient;

    @InjectMocks
    BotService botService;

    @Test
    void postUpdate_ReturnsResponseFromWebClient() {

        when(botWebClient.postUpdate(any(LinkUpdate.class))).thenReturn(EXPECTED_ANSWER);

        String response = botService.postUpdate(TG_CHAT_ID, URL, List.of(DESCRIPTION), TG_CHAT_IDS);

        assertEquals(EXPECTED_ANSWER, response);

        botService.postUpdate(TG_CHAT_ID, URL, List.of(DESCRIPTION), TG_CHAT_IDS);

        verify(botWebClient, times(2)).postUpdate(any(LinkUpdate.class));
    }
}

