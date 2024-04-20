package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.LinkRepository;
import edu.java.scrapper.repository.query.LinksQuery;
import edu.java.scrapper.repository.query.UserLinkQuery;
import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class JdbcLinkRepository implements LinkRepository {

    public static final String ID_NAME = "id";
    public static final String URL_NAME = "url";
    public static final String LAST_CHECK_NAME = "last_check";
    private final LinksQuery linksQuery;
    private final UserLinkQuery userLinkQuery;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Link exists(URI link) {
        List<Link> links = jdbcTemplate.query(linksQuery.getSelectLinkByUrl(), (rs, rowNum) -> {
                Link existingLink = new Link();
                existingLink.setId(rs.getLong(ID_NAME));
                existingLink.setLink(rs.getString(URL_NAME));
                Timestamp timestamp = rs.getTimestamp(LAST_CHECK_NAME);
                existingLink.setLastCheck(timestamp != null ? timestamp.toLocalDateTime() : null);
                return existingLink;
            },
            link.toString()
        );

        if (links.isEmpty()) {
            return null;
        }

        return links.get(0);
    }

    @Override
    public Link add(long id, URI link) {

        Timestamp now = Timestamp.valueOf(OffsetDateTime.now(ZoneOffset.UTC).toLocalDateTime());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(linksQuery.getInsertLink(), new String[] {ID_NAME});
            statement.setString(1, link.toString());
            statement.setTimestamp(
                2,
                now
            );
            return statement;
        }, keyHolder);

        Long linkId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return new Link().setId(linkId).setLink(link.toString()).setLastCheck(now.toLocalDateTime());
    }

    @Override
    public void updateLastCheck(Link link) {
        jdbcTemplate.update(
            linksQuery.getUpdateLastCheckTime(),
            Timestamp.valueOf(OffsetDateTime.now(ZoneOffset.UTC).toLocalDateTime()),
            link.getId()
        );
    }

    @Override
    public void delete(long id, URI link) {

        List<Long> linkIds = jdbcTemplate.queryForList(linksQuery.getSelectLinkIdByUrl(), Long.class, link.toString());

        if (!linkIds.isEmpty()) {
            jdbcTemplate.update(userLinkQuery.getDeleteFromUserlink(), id, linkIds.get(0));
        }

        jdbcTemplate.update(linksQuery.getDeleteFromLinks(), linkIds.get(0));
    }

    @Override
    public Collection<Link> listAll() {
        return jdbcTemplate.query(
            linksQuery.getSelectAllLinks(),
            (rs, rowNum) -> {
                Link link = new Link();
                link.setId(rs.getLong(ID_NAME));
                link.setLink(rs.getString(URL_NAME));
                Timestamp timestamp = rs.getTimestamp(LAST_CHECK_NAME);
                link.setLastCheck(timestamp != null ? timestamp.toLocalDateTime() : null);
                return link;
            }
        );
    }

    public Collection<Link> findLinksUpdatedMoreThanNMinutesAgo(long minutes) {
        LocalDateTime minutesAgo = LocalDateTime.now().minusMinutes(minutes);

        return jdbcTemplate.query(
            linksQuery.getSelectLinksByLastCheckBefore(),
            new Object[] {Timestamp.valueOf(minutesAgo)},
            (rs, rowNum) -> {
                Link link = new Link();
                link.setId(rs.getLong(ID_NAME));
                link.setLink(rs.getString(URL_NAME));
                link.setLastCheck(rs.getTimestamp(LAST_CHECK_NAME).toLocalDateTime());
                return link;
            }
        );
    }

}
