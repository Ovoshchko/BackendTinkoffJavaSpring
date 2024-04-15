package edu.java.scrapper.repository;

import edu.java.scrapper.model.Link;
import java.net.URI;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;

public interface LinkRepository {

    Link exists(URI link);

    @Transactional
    Link add(long id, URI link);

    @Transactional
    void updateLastCheck(Link link);

    @Transactional
    void delete(long id, URI link);

    Collection<Link> listAll();

    Collection<Link> findLinksUpdatedMoreThanNMinutesAgo(long minutes);
}
