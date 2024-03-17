package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.model.User;
import edu.java.scrapper.repository.UserRepository;
import java.sql.ResultSet;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@AllArgsConstructor
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users;";
        return jdbcTemplate.query(query, (ResultSet resultSet, int rowNum) ->
            new User(
                resultSet.getLong("tg_id"),
                resultSet.getDate("created_at").toLocalDate()
            )
        );
    }

    @Override
    @Transactional
    public Integer remove(long id) {
        String query = "DELETE FROM users WHERE tg_id = ?";
        return jdbcTemplate.update(query, id);
    }

    @Override
    @Transactional
    public Integer add(long id) {
        String query = "INSERT INTO users VALUES (?, now());";
        return jdbcTemplate.update(query, id);
    }
}
