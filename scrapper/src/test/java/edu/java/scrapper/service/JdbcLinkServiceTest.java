package edu.java.scrapper.service;

import edu.java.scrapper.dto.request.AddLinkRequest;
import edu.java.scrapper.dto.request.RemoveLinkRequest;
import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import edu.java.scrapper.repository.jdbc.JdbcUserLinkRepository;
import edu.java.scrapper.service.link.JdbcLinkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JdbcLinkServiceTest {

    @Mock
    private JdbcLinkRepository jdbcLinkRepository;
    @Mock
    private JdbcUserLinkRepository jdbcUserLinkRepository;
    @InjectMocks
    private JdbcLinkService jdbcLinkService;

    @Test
    void getAllLinks_ReturnsListOfLinks() {
        long tgChatId = 12345L;

        Link link = new Link(tgChatId, "https://github.com", LocalDateTime.now());

        when(jdbcUserLinkRepository.getAllLinksByUserId(tgChatId)).thenReturn(List.of(link));

        List<LinkResponse> responseEntity = jdbcLinkService.getAllLinks(tgChatId).links();

        assertEquals(1, responseEntity.size());
        assertEquals(link.getLink(), responseEntity.get(0).url().toString());
    }

    @Test
    void addLink_ReturnsLinkResponse() {
        long tgChatId = 12345L;

        URI link = URI.create("https://example.com");
        AddLinkRequest addLinkRequest = new AddLinkRequest(link);

        when(jdbcLinkRepository.add(tgChatId, addLinkRequest.link())).thenReturn(new LinkResponse(tgChatId, link));

        LinkResponse body = jdbcLinkService.addLink(tgChatId, addLinkRequest);

        assertEquals(tgChatId, body.id());
        assertEquals(link, body.url());
    }

    @Test
    void deleteLink_ReturnsLinkResponse() {
        long tgChatId = 12345L;

        URI link = URI.create("https://example.com");
        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest(link);

        when(jdbcLinkRepository.delete(tgChatId, link)).thenReturn(new LinkResponse(tgChatId, link));

        LinkResponse responseEntity = jdbcLinkService.deleteLink(tgChatId, removeLinkRequest);

        assertEquals(tgChatId, responseEntity.id());
        assertEquals(link, responseEntity.url());
    }

}

