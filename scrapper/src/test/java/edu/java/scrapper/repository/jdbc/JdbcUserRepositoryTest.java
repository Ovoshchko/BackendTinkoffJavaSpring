package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.model.User;
import edu.java.scrapper.repository.jdbc.JdbcUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcUserRepositoryTest extends IntegrationTest {

    @Autowired
    private JdbcUserRepository jdbcUserRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void findAll() {
        long userId = 12345L;

        jdbcTemplate.update("INSERT INTO users VALUES (12345, now());");

        List<User> users = jdbcUserRepository.findAll();

        assertEquals(1, users.size());
        assertEquals(users.get(0).tgId(), userId);
    }

    @Test
    @Transactional
    @Rollback
    void remove() {
        long userId = 12345L;

        jdbcTemplate.update("INSERT INTO users VALUES (12345, now());");

        jdbcUserRepository.remove(userId);

        List<User> users = jdbcTemplate.query("SELECT * FROM users;", (ResultSet resultSet, int rowNum) ->
            new User(
                resultSet.getLong("tg_id"),
                resultSet.getDate("created_at").toLocalDate()
            )
        );

        assertEquals(0, users.size());
    }

    @Test
    @Transactional
    @Rollback
    void add() {
        long userId = 12345L;

        jdbcUserRepository.add(userId);

        List<User> users = jdbcTemplate.query("SELECT * FROM users;", (ResultSet resultSet, int rowNum) ->
            new User(
                resultSet.getLong("tg_id"),
                resultSet.getDate("created_at").toLocalDate()
            )
        );

        assertEquals(1, users.size());
        assertEquals(users.get(0).tgId(), userId);

    }
}
