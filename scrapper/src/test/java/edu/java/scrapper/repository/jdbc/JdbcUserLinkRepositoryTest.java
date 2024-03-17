package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.model.Link;
import edu.java.scrapper.repository.jdbc.JdbcUserLinkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcUserLinkRepositoryTest extends IntegrationTest {

    @Autowired
    private JdbcUserLinkRepository jdbcUserLinkRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    void getAllLinksByUserId() {

        Long userId = 12345L;
        URI linkUri = URI.create("https://example.com");

        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement("INSERT INTO users VALUES (?, now());");
            statement.setLong(1, userId);
            return statement;
        });

        jdbcTemplate.update(con -> {
            PreparedStatement statement =
                con.prepareStatement("INSERT INTO links (url, last_check) VALUES (?, now());");
            statement.setString(1, linkUri.toString());
            return statement;
        });

        jdbcTemplate.update(con -> {
            PreparedStatement statement =
                con.prepareStatement("INSERT INTO userlink VALUES (?, 1);");
            statement.setLong(1, userId);
            return statement;
        });

        List<Link> links = jdbcUserLinkRepository.getAllLinksByUserId(userId).stream().toList();

        assertEquals(1, links.size());
        assertEquals(linkUri.toString(), links.get(0).getLink());
    }
}
