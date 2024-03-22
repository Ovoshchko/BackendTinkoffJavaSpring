package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.domain.jooq.linkviewer.Tables;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.UserLinkRepository;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JooqUserLinkRepository implements UserLinkRepository {

    private final DSLContext dsl;

    @Override
    public Collection<Link> getAllLinksByUserId(long id) {
        return dsl.select()
            .from(Tables.LINKS)
            .join(Tables.USERLINK)
            .on(Tables.LINKS.ID.eq(Tables.USERLINK.LINK_ID))
            .where(Tables.USERLINK.USER_ID.eq(id))
            .fetch()
            .map(rec -> {
                String url = rec.get(Tables.LINKS.URL);
                return new Link(
                    rec.get(Tables.LINKS.ID),
                    url,
                    rec.get(Tables.LINKS.LAST_CHECK)
                );
            });
    }

    @Override
    public Collection<Long> getAllUsersByLink(Link link) {
        return dsl.select(Tables.USERLINK.USER_ID)
            .from(Tables.USERLINK)
            .where(Tables.USERLINK.LINK_ID.eq(link.getId()))
            .fetchInto(Long.class);
    }
}
