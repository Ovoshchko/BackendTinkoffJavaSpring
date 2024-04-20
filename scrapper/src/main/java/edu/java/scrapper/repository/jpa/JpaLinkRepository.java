package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.LinkRepository;
import edu.java.scrapper.repository.jpa.dao.LinkDao;
import edu.java.scrapper.repository.jpa.dao.UserLinkDao;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaLinkRepository implements LinkRepository {

    private final LinkDao linkDao;
    private final UserLinkDao userLinkDao;

    @Override
    public Link exists(URI link) {
        return linkDao.findByLink(link.toString());
    }

    @Override
    public Link add(long id, URI link) {
        return linkDao.saveAndFlush(new Link().setLink(link.toString())
            .setLastCheck(OffsetDateTime.now(ZoneOffset.UTC).toLocalDateTime()));
    }

    @Override
    public void updateLastCheck(Link link) {
        linkDao.updateLastCheck(link.getId(), OffsetDateTime.now(ZoneOffset.UTC).toLocalDateTime());
    }

    @Override
    public void delete(long id, URI link) {
        userLinkDao.deleteByUserLinkIdUserTgIdAndUserLinkIdLinkLink(id, link.toString());
        linkDao.deleteByLink(link.toString());
        userLinkDao.flush();
        linkDao.flush();
    }

    @Override
    public Collection<Link> listAll() {
        return linkDao.findAll();
    }

    @Override
    public Collection<Link> findLinksUpdatedMoreThanNMinutesAgo(long minutes) {
        return linkDao.findByLastCheckBefore(OffsetDateTime.now(ZoneOffset.UTC).minusMinutes(minutes)
            .toLocalDateTime());
    }
}
