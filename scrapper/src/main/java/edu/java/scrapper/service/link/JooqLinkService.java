package edu.java.scrapper.service.link;

import edu.java.scrapper.dto.request.AddLinkRequest;
import edu.java.scrapper.dto.request.RemoveLinkRequest;
import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.dto.response.ListLinksResponse;
import edu.java.scrapper.exception.NotFoundException;
import edu.java.scrapper.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JooqLinkService implements LinkService {

    private final LinkRepository jooqLinkRepository;

    @Override
    public ListLinksResponse getAllLinks(Long tgChatId) {
        return null;
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
