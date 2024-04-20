package edu.java.scrapper.repository;

import edu.java.scrapper.model.Link;
import java.util.Collection;

public interface UserLinkRepository {

    Collection<Link> getAllLinksByUserId(long id);

    Collection<Long> getAllUsersByLink(Link link);
}
