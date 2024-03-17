package edu.java.scrapper.controller;

import edu.java.scrapper.dto.request.AddLinkRequest;
import edu.java.scrapper.dto.request.RemoveLinkRequest;
import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.dto.response.ListLinksResponse;
import edu.java.scrapper.service.link.JdbcLinkService;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LinkControllerTest {

    @Mock
    JdbcLinkService jdbcLinkService;

    @InjectMocks
    LinkController linkController;

    @Test
    void getAllLinks_ReturnsListOfLinks_WhenCalled() {
        long tgChatId = 12345L;
        List<LinkResponse> linkResponses = List.of(new LinkResponse(1L, URI.create("https://ok.com")));
        ListLinksResponse expectedResponse = new ListLinksResponse(linkResponses, linkResponses.size());

        when(jdbcLinkService.getAllLinks(tgChatId)).thenReturn(expectedResponse);

        ResponseEntity responseEntity = linkController.getAllLinks(tgChatId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void addLink_ReturnsLinkResponse_WhenCalled() {
        long tgChatId = 12345L;
        String url = "https://ok.com";
        AddLinkRequest addLinkRequest = new AddLinkRequest(URI.create(url));
        LinkResponse expectedResponse = new LinkResponse(1L, URI.create(url));

        when(jdbcLinkService.addLink(tgChatId, addLinkRequest)).thenReturn(expectedResponse);

        ResponseEntity<LinkResponse> responseEntity = linkController.addLink(tgChatId, addLinkRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void deleteLink_ReturnsLinkResponse_WhenCalled() {
        long tgChatId = 12345L;
        String url = "https://ok.com";
        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest(URI.create(url));
        LinkResponse expectedResponse = new LinkResponse(1L, URI.create(url));

        when(jdbcLinkService.deleteLink(tgChatId, removeLinkRequest)).thenReturn(expectedResponse);

        ResponseEntity<LinkResponse> responseEntity = linkController.deleteLink(tgChatId, removeLinkRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
}

