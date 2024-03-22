package edu.java.scrapper.repository;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.model.User;
import edu.java.scrapper.repository.UserRepository;
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
class UserRepositoryTest extends IntegrationTest {

    private static final long USER_ID = 12345L;
    @Autowired
    private List<UserRepository> userRepositories;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    void findAll() {

        jdbcTemplate.update("INSERT INTO users VALUES (12345, now());");

        for (UserRepository userRepository: userRepositories) {
            List<User> users = userRepository.findAll();

            assertEquals(1, users.size());
            assertEquals(users.get(0).tgId(), USER_ID);
        }
    }

    @Test
    @Transactional
    @Rollback
    void remove() {

        for (UserRepository userRepository: userRepositories) {
            jdbcTemplate.update("INSERT INTO users VALUES (12345, now());");

            userRepository.remove(USER_ID);

            List<User> users = jdbcTemplate.query("SELECT * FROM users;", (ResultSet resultSet, int rowNum) ->
                new User(
                    resultSet.getLong("tg_id"),
                    resultSet.getDate("created_at").toLocalDate()
                )
            );

            assertEquals(0, users.size());
        }
    }

    @Test
    @Transactional
    @Rollback
    void add() {

        for (UserRepository userRepository: userRepositories) {
            jdbcTemplate.update("DELETE FROM users;");
            userRepository.add(USER_ID);

            List<User> users = jdbcTemplate.query("SELECT * FROM users;", (ResultSet resultSet, int rowNum) ->
                new User(
                    resultSet.getLong("tg_id"),
                    resultSet.getDate("created_at").toLocalDate()
                )
            );

            assertEquals(1, users.size());
            assertEquals(users.get(0).tgId(), USER_ID);
        }

    }
}
