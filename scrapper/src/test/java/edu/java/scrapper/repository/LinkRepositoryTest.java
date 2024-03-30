package edu.java.scrapper.repository;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.model.Link;
import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LinkRepositoryTest extends IntegrationTest {

    public static final String USER_ID_NAME = "user_id";
    private static final long USER_ID = 12345L;
    private static final URI LINK_URI = URI.create("https://example.com");
    private static final URI LINK_URI_2 = URI.create("https://notexample.com");
    private static final String ID_NAME = "id";
    private static final String URL_NAME = "url";
    private static final String LAST_CHECK_NAME = "last_check";
    private static final long MINUTES = 30;
    private static final long DAYS = 5;

    @Autowired
    private List<LinkRepository> linkRepositories;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setInit() {
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement("INSERT INTO users VALUES (?, ?);");
            statement.setLong(1, USER_ID);
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            return statement;
        });
    }

    @AfterEach
    void setEnd() {
        jdbcTemplate.update("DELETE FROM userlink;");
        jdbcTemplate.update("DELETE FROM links;");
        jdbcTemplate.update("DELETE FROM users;");
    }

    @Test
    @Transactional
    @Rollback
    void add() {

        for (LinkRepository linkRepository : linkRepositories) {
            jdbcTemplate.update("DELETE FROM userlink;");
            jdbcTemplate.update("DELETE FROM links;");

            LinkResponse response = linkRepository.add(USER_ID, LINK_URI);

            List<Link> links = jdbcTemplate.query(
                "SELECT * FROM links;",
                (rs, rowNum) -> {
                    Link link = new Link();
                    link.setId(rs.getLong(ID_NAME));
                    link.setLink(rs.getString(URL_NAME));
                    Timestamp timestamp = rs.getTimestamp(LAST_CHECK_NAME);
                    link.setLastCheck(timestamp != null ? timestamp.toLocalDateTime() : null);
                    return link;
                }
            );

            assertEquals(LINK_URI.toString(), response.url().toString());
            assertEquals(USER_ID, response.id());
            assertEquals(LINK_URI.toString(), links.get(0).getLink());
        }
    }

    @Test
    @Transactional
    @Rollback
    void delete() {

        for (LinkRepository linkRepository : linkRepositories) {

            jdbcTemplate.update(con -> {
                PreparedStatement statement =
                    con.prepareStatement("INSERT INTO links (url, last_check) VALUES (?, ?);");
                statement.setString(1, LINK_URI.toString());
                statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                return statement;
            });

            Long linkId = jdbcTemplate.queryForObject(
                "SELECT id FROM links WHERE url = ?",
                new Object[] {LINK_URI.toString()},
                Long.class
            );

            jdbcTemplate.update(con -> {
                PreparedStatement statement =
                    con.prepareStatement("INSERT INTO userlink VALUES (?, ?);");
                statement.setLong(1, USER_ID);
                statement.setLong(2, linkId);
                return statement;
            });

            linkRepository.delete(USER_ID, LINK_URI);

            List<Link> links = jdbcTemplate.query(
                "SELECT * FROM links;",
                (rs, rowNum) -> {
                    Link link = new Link();
                    link.setId(rs.getLong(ID_NAME));
                    link.setLink(rs.getString(URL_NAME));
                    Timestamp timestamp = rs.getTimestamp(LAST_CHECK_NAME);
                    link.setLastCheck(timestamp != null ? timestamp.toLocalDateTime() : null);
                    return link;
                }
            );

            assertEquals(0, links.size());

            List<Long> userLinks = jdbcTemplate.query(
                "SELECT * FROM userlink;",
                (rs, rowNum) -> rs.getLong(USER_ID_NAME)
            );

            assertEquals(0, userLinks.size());
        }
    }

    @Test
    @Rollback
    void listAll() {

        jdbcTemplate.update(con -> {
            PreparedStatement statement =
                con.prepareStatement("INSERT INTO links (url, last_check) VALUES (?, ?);");
            statement.setString(1, LINK_URI.toString());
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            return statement;
        });

        for (LinkRepository linkRepository : linkRepositories) {
            List<Link> response = linkRepository.listAll().stream().toList();

            assertEquals(LINK_URI.toString(), response.get(0).getLink());
            assertEquals(1, response.size());
        }
    }

    @Test
    @Transactional
    @Rollback
    void findLinksUpdatedMoreThanNMinutesAgo() {

        LocalDateTime olderThanNMinutes = LocalDateTime.now().plusDays(DAYS);
        LocalDateTime withinNMinutes = LocalDateTime.now().minusDays(DAYS);

        jdbcTemplate.update(con -> {
            PreparedStatement statement =
                con.prepareStatement("INSERT INTO links (url, last_check) VALUES (?, ?);");
            statement.setString(1, LINK_URI.toString());
            statement.setTimestamp(2, Timestamp.valueOf(olderThanNMinutes));
            return statement;
        });

        jdbcTemplate.update(con -> {
            PreparedStatement statement =
                con.prepareStatement("INSERT INTO links (url, last_check) VALUES (?, ?);");
            statement.setString(1, LINK_URI_2.toString());
            statement.setTimestamp(2, Timestamp.valueOf(withinNMinutes));
            return statement;
        });

        for (LinkRepository linkRepository : linkRepositories) {

            List<Link> response = linkRepository.findLinksUpdatedMoreThanNMinutesAgo(MINUTES).stream().toList();

            assertEquals(1, response.size());
            assertTrue(response.get(0).getLastCheck().isBefore(withinNMinutes));
            assertEquals(LINK_URI_2.toString(), response.get(0).getLink());
        }
    }
}
