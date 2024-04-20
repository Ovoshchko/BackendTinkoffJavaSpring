package edu.java.scrapper.repository;

import edu.java.scrapper.model.Link;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;

public interface UserLinkRepository {

    String USER_NOT_FOUND = "Пользователь не зарегистрирован. Пожалуйста, сделайте это)";

    Collection<Link> getAllLinksByUserId(long id);

    Collection<Long> getAllUsersByLink(Link link);

    @Transactional
    void add(long userId, Link link);

    @Transactional
    void delete(long userId, Link link);
}
