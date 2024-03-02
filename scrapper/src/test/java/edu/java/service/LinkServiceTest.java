package edu.java.service;

import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.net.URI;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkServiceTest {

    private LinkService linkService;

    @BeforeEach
    void setUp() {
        linkService = new LinkService();
    }

    @Test
    void getAllLinks_ReturnsListOfLinks_WhenCalled() {
        long tgChatId = 12345L;

        ResponseEntity<ListLinksResponse> responseEntity = linkService.getAllLinks(tgChatId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ListLinksResponse body = responseEntity.getBody();
        assertEquals(1, body.size());
        assertEquals(new LinkResponse(1L, URI.create("https://github.com")), body.links().get(0));
    }

    @Test
    void addLink_ReturnsLinkResponse_WhenCalled() {
        long tgChatId = 12345L;
        AddLinkRequest addLinkRequest = new AddLinkRequest("https://example.com");

        ResponseEntity<LinkResponse> responseEntity = linkService.addLink(tgChatId, addLinkRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        LinkResponse body = responseEntity.getBody();
        assertEquals(1L, body.id());
        assertEquals(URI.create("http://github.com"), body.url());
    }

    @Test
    void deleteLink_ReturnsLinkResponse_WhenCalled() {
        long tgChatId = 12345L;
        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest("https://example.com");

        ResponseEntity<LinkResponse> responseEntity = linkService.deleteLink(tgChatId, removeLinkRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        LinkResponse body = responseEntity.getBody();
        assertEquals(1L, body.id());
        assertEquals(URI.create("http://stackoverflow.com"), body.url());
    }
}

