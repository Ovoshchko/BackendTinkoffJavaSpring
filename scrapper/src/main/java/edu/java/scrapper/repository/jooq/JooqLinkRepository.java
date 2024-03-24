package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.domain.jooq.linkviewer.Tables;
import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.LinkRepository;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static org.jooq.impl.DSL.select;

@Repository
@RequiredArgsConstructor
public class JooqLinkRepository implements LinkRepository {

    private final DSLContext dsl;

    @Override
    @Transactional
    public LinkResponse add(long id, URI link) {
        long idResponse =
            dsl.select(Tables.LINKS.ID).from(Tables.LINKS).where(Tables.LINKS.URL.eq(link.toString())).execute();

        if (idResponse == 0) {
            idResponse = Objects.requireNonNull(dsl.insertInto(Tables.LINKS).set(Tables.LINKS.URL, link.toString())
                    .set(Tables.LINKS.LAST_CHECK, LocalDateTime.now()).returning(Tables.LINKS.ID)
                    .fetchOne())
                .getValue(Tables.LINKS.ID);
        }

        dsl.insertInto(Tables.USERLINK).set(Tables.USERLINK.USER_ID, id).set(Tables.USERLINK.LINK_ID, idResponse)
            .execute();

        return new LinkResponse(id, link);
    }

    @Override
    @Transactional
    public LinkResponse delete(long id, URI link) {
        dsl.deleteFrom(Tables.USERLINK).where(Tables.USERLINK.LINK_ID.in(select(Tables.LINKS.ID).from(Tables.LINKS)
            .where(Tables.LINKS.URL.in(link.toString())))).execute();
        dsl.deleteFrom(Tables.LINKS).where(Tables.LINKS.URL.eq(link.toString())).execute();
        return new LinkResponse(id, link);
    }

    @Override
    public Collection<Link> listAll() {
        return dsl.selectFrom(Tables.LINKS)
            .fetch()
            .map(rec -> {
                String url = rec.get(Tables.LINKS.URL);
                return new Link(
                    rec.getId(),
                    url,
                    rec.getLastCheck()
                );
            });
    }

    @Override
    public Collection<Link> findLinksUpdatedMoreThanNMinutesAgo(long minutes) {
        return null;
    }
}
