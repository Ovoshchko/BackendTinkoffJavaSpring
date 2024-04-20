package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.LinkRepository;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Objects;
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
    @Transactional
    public LinkResponse add(long id, URI link) {
        long idResponse =
            dsl.select(LINKS.ID).from(LINKS).where(LINKS.URL.eq(link.toString())).execute();

        if (idResponse == 0) {
            idResponse = Objects.requireNonNull(dsl.insertInto(LINKS).set(LINKS.URL, link.toString())
                    .set(LINKS.LAST_CHECK, LocalDateTime.now().atOffset(ZoneOffset.UTC).toLocalDateTime())
                    .returning(LINKS.ID)
                    .fetchOne())
                .getValue(LINKS.ID);
        }

        dsl.insertInto(USERLINK).set(USERLINK.USER_ID, id).set(USERLINK.LINK_ID, idResponse)
            .execute();

        return new LinkResponse(id, link);
    }

    @Override
    @Transactional
    public LinkResponse delete(long id, URI link) {
        dsl.deleteFrom(USERLINK).where(USERLINK.LINK_ID.in(select(LINKS.ID).from(LINKS)
            .where(LINKS.URL.in(link.toString())))).execute();
        dsl.deleteFrom(LINKS).where(LINKS.URL.eq(link.toString())).execute();
        return new LinkResponse(id, link);
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
