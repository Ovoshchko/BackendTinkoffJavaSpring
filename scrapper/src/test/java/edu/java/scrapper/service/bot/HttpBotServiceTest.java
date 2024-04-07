package edu.java.scrapper.service.bot;

import edu.java.scrapper.clients.bot.BotWebClient;
import edu.java.scrapper.dto.request.LinkUpdate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.net.URI;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HttpBotServiceTest {

    private static final long TG_CHAT_ID = 1L;
    private static final URI URL = URI.create("https://example.com");
    private static final List<String> DESCRIPTION = List.of("Test");
    private static final List<Long> TG_CHAT_IDS = List.of(1L, 2L);
    private static final LinkUpdate LINK_UPDATE = new LinkUpdate(TG_CHAT_ID, URL, DESCRIPTION, TG_CHAT_IDS);

    private static final String EXPECTED_ANSWER = "Success";
    @Mock
    BotWebClient botWebClient;

    @InjectMocks
    HttpBotService httpBotService;

    @Test
    void postUpdate_ReturnsResponseFromWebClient() {

        when(botWebClient.postUpdate(any(LinkUpdate.class))).thenReturn(EXPECTED_ANSWER);

        String response = httpBotService.postUpdate(LINK_UPDATE);

        assertEquals(EXPECTED_ANSWER, response);

        httpBotService.postUpdate(LINK_UPDATE);

        verify(botWebClient, times(2)).postUpdate(any(LinkUpdate.class));
    }
}

