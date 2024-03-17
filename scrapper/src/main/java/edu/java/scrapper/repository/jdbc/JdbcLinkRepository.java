package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.LinkRepository;
import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
@SuppressWarnings("MultipleStringLiterals")
public class JdbcLinkRepository implements LinkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public LinkResponse add(long id, URI link) {
        String query = "SELECT id FROM links WHERE url = ?;";
        List<Long> linkIds = jdbcTemplate.queryForList(query, Long.class, link.toString());

        Long linkId;
        if (linkIds.isEmpty()) {
            String finalQuery = "INSERT INTO links (url, last_check) VALUES (?, ?);";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement statement = con.prepareStatement(finalQuery, new String[] {"id"});
                statement.setString(1, link.toString());
                statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                return statement;
            }, keyHolder);
            linkId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        } else {
            linkId = linkIds.get(0);
        }

        query = "INSERT INTO userlink VALUES (?,?);";
        jdbcTemplate.update(query, id, linkId);

        return new LinkResponse(id, link);
    }

    @Override
    @Transactional
    public LinkResponse delete(long id, URI link) {
        String query = "SELECT id FROM links WHERE url = ?;";
        List<Long> linkIds = jdbcTemplate.queryForList(query, Long.class, link.toString());

        if (!linkIds.isEmpty()) {
            query = "DELETE FROM userlink WHERE (user_id = ?) AND (link_id = ?);";
            jdbcTemplate.update(query, id, linkIds.get(0));
        }

        return new LinkResponse(id, link);
    }

    @Override
    public Collection<Link> listAll() {
        String query = "SELECT * FROM links;";
        List<Link> links = jdbcTemplate.query(
            query,
            (rs, rowNum) -> {
                Link link = new Link();
                link.setId(rs.getLong("id"));
                link.setLink(rs.getString("url"));
                Timestamp timestamp = rs.getTimestamp("last_check");
                link.setLastCheck(timestamp != null ? timestamp.toLocalDateTime() : null);
                return link;
            }
        );
        return links;
    }

    public Collection<Link> findLinksUpdatedMoreThanNMinutesAgo(long minutes) {
        LocalDateTime minutesAgo = LocalDateTime.now().minusMinutes(minutes);
        String query = "SELECT * FROM links WHERE last_updated < ?";

        return jdbcTemplate.query(con -> {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setTimestamp(1, Timestamp.valueOf(minutesAgo));
            return statement;
        }, (rs, rowNum) -> {
            Link link = new Link();
            link.setId(rs.getLong("id"));
            link.setLink(rs.getString("url"));
            link.setLastCheck(rs.getTimestamp("last_check").toLocalDateTime());
            return link;
        });
    }

}
