package edu.java.bot.service;

import edu.java.bot.client.scrapper.ScrapperWebClient;
import edu.java.bot.dto.request.AddLinkRequest;
import edu.java.bot.dto.request.RemoveLinkRequest;
import edu.java.bot.dto.response.LinkResponse;
import java.net.URI;
import java.util.List;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScrapperServiceTest {

    private final static LinkResponse VALID_LINK_RESPONSE = new LinkResponse(1L, URI.create("https://example.com"));
    private final static LinkResponse SECOND_VALID_LINK_RESPONSE = new LinkResponse(1L, URI.create("https://ok.com"));

    @Mock

    private ScrapperWebClient scrapperWebClient;
    @InjectMocks
    private ScrapperService scrapperService;

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

    @ParameterizedTest
    @MethodSource("provideLinkResponse")
    void getAllLinksSuccess(LinkResponse linkResponse) {
        List<LinkResponse> links = List.of(
            linkResponse,
            linkResponse
        );

        when(scrapperWebClient.getAllLinks(any(Long.class))).thenReturn(links);

        List<String> result = scrapperService.getAllLinks(VALID_LINK_RESPONSE.id());

        assertEquals(links.stream().map(l -> l.url().toString()).toList(), result);
    }

    @ParameterizedTest
    @MethodSource("provideLinkResponse")
    void addLink_Success(LinkResponse linkResponse) {
        when(scrapperWebClient.addLink(any(Long.class), any(AddLinkRequest.class))).thenReturn(linkResponse);

        String result = scrapperService.addLink(linkResponse.id(), linkResponse.url().toString());

        assertEquals(linkResponse.url().toString(), result);
    }

    @ParameterizedTest
    @MethodSource("provideLinkResponse")
    void deleteLink_Success(LinkResponse linkResponse) {
        when(scrapperWebClient.deleteLink(
            any(Long.class),
            any(RemoveLinkRequest.class)
        )).thenReturn(linkResponse);

        String result = scrapperService.deleteLink(linkResponse.id(), linkResponse.url().toString());

        assertEquals(linkResponse.url().toString(), result);
    }

    public static Stream<Arguments> provideLinkResponse() {
        return Stream.of(
            Arguments.of(VALID_LINK_RESPONSE),
            Arguments.of(SECOND_VALID_LINK_RESPONSE)
        );
    }
}


