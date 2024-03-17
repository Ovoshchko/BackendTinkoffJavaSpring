package edu.java.scrapper.repository.User;

import edu.java.scrapper.model.User;
import java.sql.ResultSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserRepository implements UserRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users;";
        return jdbcTemplate.query(query, (ResultSet resultSet, int rowNum) -> {
            User user = new User();
            user.setTgId(resultSet.getLong("tg_id"));
            user.setCreatedAt(resultSet.getDate("created_at").toLocalDate());
            return user;
        });
    }

    @Override
    public Integer remove(long id) {
        String query = "DELETE FROM users WHERE tg_id = ?";
        return jdbcTemplate.update(query, id);
    }

    @Override
    public Integer add(long id) {
        String query = "INSERT INTO users VALUES (?, now());";
        return jdbcTemplate.update(query, id);
    }
}
