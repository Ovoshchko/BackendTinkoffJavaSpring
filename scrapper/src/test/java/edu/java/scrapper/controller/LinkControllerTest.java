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

    public static final URI URL = URI.create("https://ok.com");
    public static final long TG_CHAT_ID = 12345L;
    @Mock
    JdbcLinkService jdbcLinkService;

    @InjectMocks
    LinkController linkController;

    @Test
    void getAllLinks_ReturnsListOfLinks_WhenCalled() {
        List<LinkResponse> linkResponses = List.of(new LinkResponse(TG_CHAT_ID, URL));
        ListLinksResponse expectedResponse = new ListLinksResponse(linkResponses, linkResponses.size());

        when(jdbcLinkService.getAllLinks(TG_CHAT_ID)).thenReturn(expectedResponse);

        ResponseEntity responseEntity = linkController.getAllLinks(TG_CHAT_ID);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void addLink_ReturnsLinkResponse_WhenCalled() {

        AddLinkRequest addLinkRequest = new AddLinkRequest(URL);
        LinkResponse expectedResponse = new LinkResponse(TG_CHAT_ID, URL);

        when(jdbcLinkService.addLink(TG_CHAT_ID, addLinkRequest)).thenReturn(expectedResponse);

        ResponseEntity<LinkResponse> responseEntity = linkController.addLink(TG_CHAT_ID, addLinkRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void deleteLink_ReturnsLinkResponse_WhenCalled() {

        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest(URL);
        LinkResponse expectedResponse = new LinkResponse(TG_CHAT_ID, URL);

        when(jdbcLinkService.deleteLink(TG_CHAT_ID, removeLinkRequest)).thenReturn(expectedResponse);

        ResponseEntity<LinkResponse> responseEntity = linkController.deleteLink(TG_CHAT_ID, removeLinkRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
}

