package edu.java.scrapper.repository.jpa.dao;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.model.User;
import edu.java.scrapper.model.UserLink;
import edu.java.scrapper.model.UserLinkId;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLinkDao extends JpaRepository<UserLink, UserLinkId> {
    @Query("SELECT ul.id.link FROM UserLink ul WHERE ul.id.user.tgId = :user_id")
    Collection<Link> findLinksByUserId(@Param("user_id") long userId);

    @Query("SELECT ul.id.user FROM UserLink ul WHERE ul.id.link = :link")
    Collection<User> findUsersByLink(@Param("link") Link link);

    void deleteByUserLinkIdUserTgIdAndUserLinkIdLinkLink(Long id, String link);
}
