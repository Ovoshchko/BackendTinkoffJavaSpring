package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.UserLinkRepository;
import edu.java.scrapper.repository.query.UserLinkQuery;
import java.sql.PreparedStatement;
import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class JdbcUserLinkRepository implements UserLinkRepository {

    public static final String ID_NAME = "id";
    public static final String URL_NAME = "url";
    public static final String LAST_CHECK_NAME = "last_check";
    public static final String USER_ID_NAME = "user_id";
    private final UserLinkQuery userLinkQuery;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Link> getAllLinksByUserId(long id) {
        return jdbcTemplate.query(
            con -> {
                PreparedStatement statement = con.prepareStatement(userLinkQuery.getSelectAllLinksByUser());
                statement.setLong(1, id);
                return statement;
            },
            (rs, rowNum) -> {
                Link link = new Link();
                link.setId(rs.getLong(ID_NAME));
                link.setLink(rs.getString(URL_NAME));
                link.setLastCheck(rs.getTimestamp(LAST_CHECK_NAME).toLocalDateTime());
                return link;
            }
        );
    }

    @Override
    public Collection<Long> getAllUsersByLink(Link link) {
        return jdbcTemplate.query(
            con -> {
                PreparedStatement statement = con.prepareStatement(userLinkQuery.getSelectUsersByLink());
                statement.setLong(1, link.getId());
                return statement;
            },
            (rs, rowNum) -> rs.getLong(USER_ID_NAME)
        );
    }
}
