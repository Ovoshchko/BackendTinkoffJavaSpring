package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.dto.response.LinkResponse;
import edu.java.scrapper.model.Link;
import java.net.URI;
import java.sql.PreparedStatement;
import java.util.List;
import edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JdbcLinkRepositoryTest extends IntegrationTest {

    @Autowired
    private JdbcLinkRepository jdbcLinkRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    void add() {
        long userId = 12345L;
        URI linkUri = URI.create("https://example.com");

        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement("INSERT INTO users VALUES (?, now());");
            statement.setLong(1, userId);
            return statement;
        });

        LinkResponse response = jdbcLinkRepository.add(userId, linkUri);

        assertEquals(linkUri.toString(), response.url().toString());
        assertEquals(userId, response.id());
    }

    @Test
    @Transactional
    @Rollback
    void delete() {
        long userId = 12345L;
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

        LinkResponse response = jdbcLinkRepository.delete(userId, linkUri);

        assertEquals(linkUri.toString(), response.url().toString());
        assertEquals(userId, response.id());

        response = jdbcLinkRepository.delete(userId, linkUri);

        assertEquals(linkUri.toString(), response.url().toString());
        assertEquals(userId, response.id());
    }

    @Test
    @Rollback
    void listAll() {
        long userId = 12345L;
        URI linkUri = URI.create("https://example.com");

        jdbcTemplate.update(con -> {
            PreparedStatement statement =
                con.prepareStatement("INSERT INTO links (url, last_check) VALUES (?, now());");
            statement.setString(1, linkUri.toString());
            return statement;
        });

        List<Link> response = jdbcLinkRepository.listAll().stream().toList();

        assertEquals(linkUri.toString(), response.get(0).getLink());
        assertEquals(1, response.size());
    }
}
