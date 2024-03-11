package edu.java.controller;

import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import edu.java.service.LinkService;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LinkControllerTest {

    @Mock
    LinkService linkService;

    @InjectMocks
    LinkController linkController;

    @Test
    void getAllLinks_ReturnsListOfLinks_WhenCalled() {
        long tgChatId = 12345L;
        List<LinkResponse> linkResponses = List.of(new LinkResponse(1L, URI.create("https://ok.com")));
        ListLinksResponse expectedResponse = new ListLinksResponse(linkResponses, linkResponses.size());

        when(linkService.getAllLinks(tgChatId)).thenReturn(ResponseEntity.ok(expectedResponse));

        ResponseEntity responseEntity = linkController.getAllLinks(tgChatId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void addLink_ReturnsLinkResponse_WhenCalled() {
        long tgChatId = 12345L;
        String url = "https://ok.com";
        AddLinkRequest addLinkRequest = new AddLinkRequest(url);
        LinkResponse expectedResponse = new LinkResponse(1L, URI.create(url));

        when(linkService.addLink(tgChatId, addLinkRequest)).thenReturn(ResponseEntity.ok(expectedResponse));

        ResponseEntity<LinkResponse> responseEntity = linkController.addLink(tgChatId, addLinkRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void deleteLink_ReturnsLinkResponse_WhenCalled() {
        long tgChatId = 12345L;
        String url = "https://ok.com";
        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest(url);
        LinkResponse expectedResponse = new LinkResponse(1L, URI.create(url));

        when(linkService.deleteLink(tgChatId, removeLinkRequest)).thenReturn(ResponseEntity.ok(expectedResponse));

        ResponseEntity<LinkResponse> responseEntity = linkController.deleteLink(tgChatId, removeLinkRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
}

