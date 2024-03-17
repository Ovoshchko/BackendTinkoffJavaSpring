package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.UserLinkRepository;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@SuppressWarnings("MultipleStringLiterals")
public class JdbcUserLinkRepository implements UserLinkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Link> getAllLinksByUserId(long id) {
        String query = "SELECT * FROM links l JOIN userlink ul ON ul.link_id = l.id WHERE ul.user_id = ?;";
        List<Link> links = jdbcTemplate.query(
            con -> {
                PreparedStatement statement = con.prepareStatement(query);
                statement.setLong(1, id);
                return statement;
            },
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

    @Override
    public Collection<Long> getAllUsersByLink(Link link) {
        String query = "SELECT user_id FROM userlink WHERE link_id = ?;";
        List<Long> users = jdbcTemplate.query(
            con -> {
                PreparedStatement statement = con.prepareStatement(query);
                statement.setLong(1, link.getId());
                return statement;
            },
            (rs, rowNum) -> {
                return rs.getLong("user_id");
            }
        );
        return users;
    }
}
