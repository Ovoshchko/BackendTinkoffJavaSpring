package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.LinkRepository;
import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.scrapper.domain.jooq.linkviewer.Tables.LINKS;
import static edu.java.scrapper.domain.jooq.linkviewer.Tables.USERLINK;
import static org.jooq.impl.DSL.select;

@Repository
@RequiredArgsConstructor
public class JooqLinkRepository implements LinkRepository {

    private final DSLContext dsl;

    @Override
    public Link exists(URI link) {
        List<Link> links = dsl.select().from(LINKS).where(LINKS.URL.eq(link.toString())).fetchInto(Link.class);

        return links.isEmpty() ? null : links.get(0);
    }

    @Override
    public Link add(long id, URI link) {
        return dsl.insertInto(LINKS).set(LINKS.URL, link.toString())
            .set(LINKS.LAST_CHECK, LocalDateTime.now().atOffset(ZoneOffset.UTC).toLocalDateTime())
            .returning()
            .fetchInto(Link.class)
            .get(0);
    }

    @Override
    public void updateLastCheck(Link link) {
        dsl.update(LINKS)
            .set(LINKS.LAST_CHECK, OffsetDateTime.now(ZoneOffset.UTC).toLocalDateTime())
            .where(LINKS.ID.eq(link.getId()))
            .execute();
    }

    @Override
    public void delete(long id, URI link) {
        dsl.deleteFrom(USERLINK).where(USERLINK.LINK_ID.in(select(LINKS.ID).from(LINKS)
            .where(LINKS.URL.in(link.toString())))).execute();
        dsl.deleteFrom(LINKS).where(LINKS.URL.eq(link.toString())).execute();
    }

    @Override
    public Collection<Link> listAll() {
        return dsl.selectFrom(LINKS)
            .fetchInto(Link.class);
    }

    @Override
    public Collection<Link> findLinksUpdatedMoreThanNMinutesAgo(long minutes) {
        return dsl.selectFrom(LINKS)
            .where(LINKS.LAST_CHECK.lessThan(LocalDateTime.now().minusMinutes(minutes)))
            .fetchInto(Link.class);
    }
}
