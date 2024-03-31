package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.model.User;
import edu.java.scrapper.model.UserLink;
import edu.java.scrapper.model.UserLinkId;
import edu.java.scrapper.repository.LinkRepository;
import edu.java.scrapper.repository.jpa.dao.LinkDao;
import edu.java.scrapper.repository.jpa.dao.UserLinkDao;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class JpaLinkRepository implements LinkRepository {

    private final LinkDao linkDao;
    private final UserLinkDao userLinkDao;

    @Override
    @Transactional
    public LinkResponse add(long id, URI link) {
        Link existingLink = linkDao.findByLink(link.toString());

        if (existingLink == null) {
            existingLink = linkDao.saveAndFlush(new Link().setLink(link.toString())
                .setLastCheck(LocalDateTime.now().atOffset(ZoneOffset.UTC).toLocalDateTime()));
        }

        userLinkDao.saveAndFlush(
            new UserLink().setUserLinkId(
                new UserLinkId().setUser(new User().setTgId(id)).setLink(existingLink)
            )
        );

        return new LinkResponse(id, link);
    }

    @Override
    @Transactional
    public LinkResponse delete(long id, URI link) {
        userLinkDao.deleteByUserLinkIdUserTgIdAndUserLinkIdLinkLink(id, link.toString());
        linkDao.deleteByLink(link.toString());
        userLinkDao.flush();
        linkDao.flush();
        return new LinkResponse(id, link);
    }

    @Override
    public Collection<Link> listAll() {
        return linkDao.findAll();
    }

    @Override
    public Collection<Link> findLinksUpdatedMoreThanNMinutesAgo(long minutes) {
        return linkDao.findByLastCheckBefore(OffsetDateTime.now(ZoneOffset.UTC).toLocalDateTime());
    }
}
