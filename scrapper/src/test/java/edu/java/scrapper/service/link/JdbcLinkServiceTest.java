package edu.java.scrapper.service.link;

import edu.java.scrapper.dto.request.AddLinkRequest;
import edu.java.scrapper.dto.request.RemoveLinkRequest;
import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import edu.java.scrapper.repository.jdbc.JdbcUserLinkRepository;
import edu.java.scrapper.service.link.JdbcLinkService;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JdbcLinkServiceTest {

    public static final long TG_CHAT_ID = 12345L;
    public static final URI URL = URI.create("https://example.com");
    public static final Link LINK = new Link(TG_CHAT_ID, URL.toString(), LocalDateTime.now());
    public static final LinkResponse LINK_RESPONSE = new LinkResponse(TG_CHAT_ID, URL);
    @Mock
    private JdbcLinkRepository jdbcLinkRepository;
    @Mock
    private JdbcUserLinkRepository jdbcUserLinkRepository;
    @InjectMocks
    private JdbcLinkService jdbcLinkService;

    @Test
    void getAllLinks_ReturnsListOfLinks() {

        when(jdbcUserLinkRepository.getAllLinksByUserId(TG_CHAT_ID)).thenReturn(List.of(LINK));

        List<LinkResponse> responseEntity = jdbcLinkService.getAllLinks(TG_CHAT_ID).links();

        assertEquals(1, responseEntity.size());
        assertEquals(LINK.getLink(), responseEntity.get(0).url().toString());
    }

    @Test
    void addLink_ReturnsLinkResponse() {

        AddLinkRequest addLinkRequest = new AddLinkRequest(URL);

        when(jdbcLinkRepository.add(TG_CHAT_ID, addLinkRequest.link())).thenReturn(LINK_RESPONSE);

        LinkResponse body = jdbcLinkService.addLink(TG_CHAT_ID, addLinkRequest);

        assertEquals(TG_CHAT_ID, body.id());
        assertEquals(URL, body.url());
    }

    @Test
    void deleteLink_ReturnsLinkResponse() {

        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest(URL);

        when(jdbcLinkRepository.delete(TG_CHAT_ID, URL)).thenReturn(LINK_RESPONSE);

        LinkResponse responseEntity = jdbcLinkService.deleteLink(TG_CHAT_ID, removeLinkRequest);

        assertEquals(TG_CHAT_ID, responseEntity.id());
        assertEquals(URL, responseEntity.url());
    }

}

