package edu.java.scrapper.service.link;

import edu.java.scrapper.dto.request.AddLinkRequest;
import edu.java.scrapper.dto.request.RemoveLinkRequest;
import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.dto.response.ListLinksResponse;
import edu.java.scrapper.exception.NotFoundException;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.LinkRepository;
import edu.java.scrapper.repository.UserLinkRepository;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JooqLinkService implements LinkService {

    private final LinkRepository jooqLinkRepository;
    private final UserLinkRepository jooqUserLinkRepository;

    @Override
    public ListLinksResponse getAllLinks(Long tgChatId) {
        Collection<Link> links = jooqUserLinkRepository.getAllLinksByUserId(tgChatId);
        List<LinkResponse> responses = new ArrayList<>();
        for (Link link : links) {
            responses.add(new LinkResponse(link.getId(), URI.create(link.getLink())));
        }
        return new ListLinksResponse(responses, responses.size());
    }

    @Override
    public LinkResponse addLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        try {
            return jooqLinkRepository.add(tgChatId, addLinkRequest.link());
        } catch (IntegrityConstraintViolationException exception) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
    }

    @Override
    public LinkResponse deleteLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        return jooqLinkRepository.delete(tgChatId, removeLinkRequest.link());
    }
}
