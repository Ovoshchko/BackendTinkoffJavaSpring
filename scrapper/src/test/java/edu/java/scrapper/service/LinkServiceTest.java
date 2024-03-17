package edu.java.scrapper.service;

import edu.java.scrapper.dto.request.AddLinkRequest;
import edu.java.scrapper.dto.request.RemoveLinkRequest;
import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.dto.response.ListLinksResponse;
import java.net.URI;
import edu.java.scrapper.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LinkServiceTest {

    private LinkService linkService;

    @BeforeEach
    void setUp() {
        linkService = new LinkService();
    }

    @Test
    void getAllLinks_ReturnsListOfLinks() {
        long tgChatId = 12345L;

        ResponseEntity<ListLinksResponse> responseEntity = linkService.getAllLinks(tgChatId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ListLinksResponse body = responseEntity.getBody();
        assertEquals(1, body.size());
        assertEquals(new LinkResponse(1L, URI.create("https://github.com")), body.links().get(0));
    }

    @Test
    void addLink_ReturnsLinkResponse() {
        long tgChatId = 12345L;
        AddLinkRequest addLinkRequest = new AddLinkRequest("https://example.com");

        ResponseEntity<LinkResponse> responseEntity = linkService.addLink(tgChatId, addLinkRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        LinkResponse body = responseEntity.getBody();
        assertEquals(1L, body.id());
        assertEquals(URI.create("http://github.com"), body.url());
    }

    @Test
    void deleteLink_ReturnsLinkResponse() {
        long tgChatId = 12345L;
        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest("https://example.com");

        ResponseEntity<LinkResponse> responseEntity = linkService.deleteLink(tgChatId, removeLinkRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        LinkResponse body = responseEntity.getBody();
        assertEquals(1L, body.id());
        assertEquals(URI.create("http://stackoverflow.com"), body.url());
    }

    @Test
    void addLink_ReturnsApiErrorResponse() {
        long tgChatId = 666L;
        AddLinkRequest addLinkRequest = new AddLinkRequest("https://slackoverflow.com");

        assertThrows(NotFoundException.class, () -> linkService.addLink(tgChatId, addLinkRequest));
    }

    @Test
    void removeLink_ReturnsApiErrorResponse() {
        long tgChatId = 666L;
        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest("https://slackoverflow.com");

        assertThrows(NotFoundException.class, () -> linkService.deleteLink(tgChatId, removeLinkRequest));
    }
}

