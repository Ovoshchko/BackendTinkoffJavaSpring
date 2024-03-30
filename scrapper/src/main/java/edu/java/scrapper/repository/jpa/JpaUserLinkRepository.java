package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.model.User;
import edu.java.scrapper.repository.UserLinkRepository;
import edu.java.scrapper.repository.jpa.dao.UserLinkDao;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaUserLinkRepository implements UserLinkRepository {

    private final UserLinkDao userLinkDao;

    @Override
    public Collection<Link> getAllLinksByUserId(long id) {
        return userLinkDao.findLinksByUserId(id);
    }

    @Override
    public Collection<Long> getAllUsersByLink(Link link) {
        return userLinkDao.findUsersByLink(link)
            .stream()
            .map(User::getTgId)
            .toList();
    }
}
