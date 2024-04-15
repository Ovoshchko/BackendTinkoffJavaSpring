package edu.java.scrapper.repository;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.model.Link;
import java.net.URI;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext
class UserLinkRepositoryTest extends IntegrationTest {

    private static final Long USER_ID = 12345L;
    private static final long LINK_ID = 12345432L;
    private static final URI LINK_URI = URI.create("https://example.com");
    @Autowired
    private List<UserLinkRepository> userLinkRepositories;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    @Transactional
    void setInit() {

        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement("INSERT INTO users VALUES (?, now());");
            statement.setLong(1, USER_ID);
            return statement;
        });

        jdbcTemplate.update(con -> {
            PreparedStatement statement =
                con.prepareStatement("INSERT INTO links (url, last_check) VALUES (?, now());");
            statement.setString(1, LINK_URI.toString());
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
    }

    @AfterEach
    @Transactional
    void setEnd() {
        jdbcTemplate.update("DELETE FROM userlink;");
        jdbcTemplate.update("DELETE FROM links;");
        jdbcTemplate.update("DELETE FROM users;");
    }

    @Test
    void getAllLinksByUserId() {
        for (UserLinkRepository userLinkRepository : userLinkRepositories) {
            List<Link> links = userLinkRepository.getAllLinksByUserId(USER_ID).stream().toList();

            assertEquals(1, links.size());
            assertEquals(LINK_URI.toString(), links.get(0).getLink());
        }
    }

    @Test
    void getAllUsersByLink() {

        Long linkId = jdbcTemplate.queryForObject(
            "SELECT id FROM links WHERE url = ?",
            new Object[] {LINK_URI.toString()},
            Long.class
        );

        for (UserLinkRepository userLinkRepository : userLinkRepositories) {
            List<Long> userIds =
                userLinkRepository.getAllUsersByLink(new Link().setId(linkId).setLink(LINK_URI.toString())
                        .setLastCheck(LocalDateTime.now()))
                    .stream().toList();

            assertEquals(1, userIds.size());
            assertEquals(USER_ID, userIds.get(0));
        }
    }
}
