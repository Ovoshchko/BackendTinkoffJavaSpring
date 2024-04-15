package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.LinkRepository;
import edu.java.scrapper.repository.query.LinksQuery;
import edu.java.scrapper.repository.query.UserLinkQuery;
import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
            link.toString());

        if (links.isEmpty()) {
            return null;
        }

        return links.get(0);
    }

    @Override
    @Transactional
    public LinkResponse add(long id, URI link) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(linksQuery.getInsertLink(), new String[] {ID_NAME});
            statement.setString(1, link.toString());
            statement.setTimestamp(
                2,
                Timestamp.valueOf(LocalDateTime.now().atOffset(ZoneOffset.UTC).toLocalDateTime())
            );
            return statement;
        }, keyHolder);

        Long linkId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        jdbcTemplate.update(userLinkQuery.getInsertIntoUserlink(), id, linkId);

        return new LinkResponse(id, link);
    }

    @Override
    @Transactional
    public LinkResponse delete(long id, URI link) {

        List<Long> linkIds = jdbcTemplate.queryForList(linksQuery.getSelectLinkIdByUrl(), Long.class, link.toString());

        if (!linkIds.isEmpty()) {
            jdbcTemplate.update(userLinkQuery.getDeleteFromUserlink(), id, linkIds.get(0));
        }

        jdbcTemplate.update(linksQuery.getDeleteFromLinks(), linkIds.get(0));

        return new LinkResponse(id, link);
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
