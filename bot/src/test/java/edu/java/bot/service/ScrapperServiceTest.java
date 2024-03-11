package edu.java.bot.service;

import edu.java.bot.client.scrapper.ScrapperWebClient;
import edu.java.bot.dto.request.AddLinkRequest;
import edu.java.bot.dto.request.RemoveLinkRequest;
import edu.java.bot.dto.response.LinkResponse;
import java.net.URI;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ScrapperServiceTest {

    private static final ScrapperWebClient scrapperWebClient = Mockito.mock(ScrapperWebClient.class);
    private ScrapperService scrapperService;

    @BeforeEach
    void setUp() {
        scrapperService = new ScrapperService(scrapperWebClient);
    }

    @Test
    void registerUserChatSuccess() {
        when(scrapperWebClient.registerUserChat(any(Long.class))).thenReturn("Success");

        String result = scrapperService.registerUserChat(1L);

        assertEquals("Success", result);
    }

    @Test
    void deleteUserChatSuccess() {
        when(scrapperWebClient.deleteUserChat(any(Long.class))).thenReturn("Success");

        String result = scrapperService.deleteUserChat(1L);

        assertEquals("Success", result);
    }

    @Test
    @SneakyThrows
    void getAllLinksSuccess() {
        List<LinkResponse> links = List.of(
            new LinkResponse(1L, new URI("https://example.com")),
            new LinkResponse(1L, new URI("https://ok.com"))
        );
        when(scrapperWebClient.getAllLinks(any(Long.class))).thenReturn(links);

        List<String> result = scrapperService.getAllLinks(1L);

        assertEquals(links.stream().map(l -> l.url().toString()).toList(), result);
    }

    @Test
    @SneakyThrows
    void addLink_Success() {
        String addLinkRequest = "https://ok.com";
        when(scrapperWebClient.addLink(any(Long.class), any(AddLinkRequest.class))).thenReturn(new LinkResponse(
            1L,
            new URI("https://ok.com")
        ));

        String result = scrapperService.addLink(1L, "https://ok.com");

        assertEquals(addLinkRequest, result);
    }

    @Test
    @SneakyThrows
    void deleteLink_Success() {
        String removeLinkRequest = "https://ok.com";
        when(scrapperWebClient.deleteLink(
            any(Long.class),
            any(RemoveLinkRequest.class)
        )).thenReturn(new LinkResponse(1L, new URI("https://ok.com")));

        String result = scrapperService.deleteLink(1L, "https://ok.com");

        assertEquals(removeLinkRequest, result);
    }
}


