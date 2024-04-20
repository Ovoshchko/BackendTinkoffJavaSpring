package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.exception.NotFoundException;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.model.User;
import edu.java.scrapper.model.UserLink;
import edu.java.scrapper.model.UserLinkId;
import edu.java.scrapper.repository.UserLinkRepository;
import edu.java.scrapper.repository.jpa.dao.UserLinkDao;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaUserLinkRepository implements UserLinkRepository {

    private final UserLinkDao userLinkDao;
    private final JpaUserRepository userRepository;

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

    @Override
    public void add(long userId, Link link) {
        User user = userRepository.findById(userId);

        if (user != null) {
            throw new NotFoundException(USER_NOT_FOUND);
        }

        userLinkDao.saveAndFlush(
            new UserLink().setUserLinkId(
                new UserLinkId().setUser(user).setLink(link)
            )
        );
    }

    @Override
    public void delete(long userId, Link link) {
        User user = userRepository.findById(userId);
        userLinkDao.delete(new UserLink().setUserLinkId(new UserLinkId().setUser(user).setLink(link)));
    }
}
