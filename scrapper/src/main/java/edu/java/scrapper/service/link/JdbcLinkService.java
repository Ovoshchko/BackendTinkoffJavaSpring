package edu.java.scrapper.service.link;

import edu.java.scrapper.dto.request.AddLinkRequest;
import edu.java.scrapper.dto.request.RemoveLinkRequest;
import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.dto.response.ListLinksResponse;
import edu.java.scrapper.exception.NotFoundException;
import edu.java.scrapper.repository.LinkRepository;
import edu.java.scrapper.repository.UserLinkRepository;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JdbcLinkService implements LinkService {

    private final LinkRepository jdbcLinkRepository;
    private final UserLinkRepository jdbcUserLinkRepository;

    public ListLinksResponse getAllLinks(Long tgChatId) {
        List<LinkResponse> linkResponses = jdbcUserLinkRepository.getAllLinksByUserId(tgChatId)
            .stream()
            .map(link -> new LinkResponse(tgChatId, URI.create(link.getLink())))
            .toList();
        return new ListLinksResponse(linkResponses, linkResponses.size());
    }

    public LinkResponse addLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        try {
            return jdbcLinkRepository.add(tgChatId, addLinkRequest.link());
        } catch (DataIntegrityViolationException exception) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
    }

    public LinkResponse deleteLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        return jdbcLinkRepository.delete(tgChatId, removeLinkRequest.link());
    }
}
