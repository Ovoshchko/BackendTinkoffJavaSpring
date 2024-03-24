package edu.java.scrapper.service.link;

import edu.java.scrapper.dto.request.AddLinkRequest;
import edu.java.scrapper.dto.request.RemoveLinkRequest;
import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import edu.java.scrapper.repository.jooq.JooqLinkRepository;
import edu.java.scrapper.repository.jooq.JooqUserLinkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JooqLinkServiceTest {

    public static final long TG_CHAT_ID = 12345L;
    public static final URI URL = URI.create("https://example.com");
    public static final Link LINK = new Link(TG_CHAT_ID, URL.toString(), LocalDateTime.now());
    public static final LinkResponse LINK_RESPONSE = new LinkResponse(TG_CHAT_ID, URL);
    @Mock
    private JooqLinkRepository jooqLinkRepository;
    @Mock
    private JooqUserLinkRepository jooqUserLinkRepository;
    @InjectMocks
    private JooqLinkService jooqLinkService;

    @Test
    void getAllLinks_ReturnsListOfLinks() {

        when(jooqUserLinkRepository.getAllLinksByUserId(TG_CHAT_ID)).thenReturn(List.of(LINK));

        List<LinkResponse> responseEntity = jooqLinkService.getAllLinks(TG_CHAT_ID).links();

        assertEquals(1, responseEntity.size());
        assertEquals(LINK.getLink(), responseEntity.get(0).url().toString());
    }

    @Test
    void addLink_ReturnsLinkResponse() {

        AddLinkRequest addLinkRequest = new AddLinkRequest(URL);

        when(jooqLinkRepository.add(TG_CHAT_ID, addLinkRequest.link())).thenReturn(LINK_RESPONSE);

        LinkResponse body = jooqLinkService.addLink(TG_CHAT_ID, addLinkRequest);

        assertEquals(TG_CHAT_ID, body.id());
        assertEquals(URL, body.url());
    }

    @Test
    void deleteLink_ReturnsLinkResponse() {

        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest(URL);

        when(jooqLinkRepository.delete(TG_CHAT_ID, URL)).thenReturn(LINK_RESPONSE);

        LinkResponse responseEntity = jooqLinkService.deleteLink(TG_CHAT_ID, removeLinkRequest);

        assertEquals(TG_CHAT_ID, responseEntity.id());
        assertEquals(URL, responseEntity.url());
    }

}
