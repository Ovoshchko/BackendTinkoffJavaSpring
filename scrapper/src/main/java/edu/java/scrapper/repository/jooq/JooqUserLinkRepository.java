package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.exception.NotFoundException;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.model.User;
import edu.java.scrapper.repository.UserLinkRepository;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import static edu.java.scrapper.domain.jooq.linkviewer.Tables.LINKS;
import static edu.java.scrapper.domain.jooq.linkviewer.Tables.USERLINK;

@Repository
@RequiredArgsConstructor
public class JooqUserLinkRepository implements UserLinkRepository {

    private final DSLContext dsl;
    private final JooqUserRepository userRepository;

    @Override
    public Collection<Link> getAllLinksByUserId(long id) {
        return dsl.select()
            .from(LINKS)
            .join(USERLINK)
            .on(LINKS.ID.eq(USERLINK.LINK_ID))
            .where(USERLINK.USER_ID.eq(id))
            .fetch()
            .map(rec -> {
                String url = rec.get(LINKS.URL);
                return new Link()
                    .setId(rec.get(LINKS.ID))
                    .setLink(url)
                    .setLastCheck(rec.get(LINKS.LAST_CHECK));
            });
    }

    @Override
    public Collection<Long> getAllUsersByLink(Link link) {
        return dsl.select(USERLINK.USER_ID)
            .from(USERLINK)
            .where(USERLINK.LINK_ID.eq(link.getId()))
            .fetchInto(Long.class);
    }

    @Override
    public void add(long userId, Link link) {
        User user = userRepository.findById(userId);

        if (user == null) {
            throw new NotFoundException(USER_NOT_FOUND);
        }

        dsl.insertInto(USERLINK)
            .set(USERLINK.USER_ID, userId)
            .set(USERLINK.LINK_ID, link.getId())
            .execute();
    }

    @Override
    public void delete(long userId, Link link) {
        dsl.deleteFrom(USERLINK)
            .where(USERLINK.USER_ID.eq(userId))
            .and(USERLINK.LINK_ID.eq(link.getId()))
            .execute();
    }
}
