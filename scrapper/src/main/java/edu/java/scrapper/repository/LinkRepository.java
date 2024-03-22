package edu.java.scrapper.repository;

import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.model.Link;
import java.net.URI;
import java.util.Collection;

public interface LinkRepository {

    LinkResponse add(long id, URI link);

    LinkResponse delete(long id, URI link);

    Collection<Link> listAll();

    Collection<Link> findLinksUpdatedMoreThanNMinutesAgo(long minutes);
}
