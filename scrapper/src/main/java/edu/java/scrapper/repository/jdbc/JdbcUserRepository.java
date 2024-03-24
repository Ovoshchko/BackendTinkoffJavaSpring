package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.model.User;
import edu.java.scrapper.repository.UserRepository;
import edu.java.scrapper.repository.query.UserQuery;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Qualifier("jdbcUserRepository")
public class JdbcUserRepository implements UserRepository {

    public static final String TG_ID_NAME = "tg_id";
    public static final String CREATED_AT_NAME = "created_at";
    private final UserQuery userQuery;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(userQuery.getSelectAllUsers(), (ResultSet resultSet, int rowNum) ->
            new User(
                resultSet.getLong(TG_ID_NAME),
                resultSet.getDate(CREATED_AT_NAME).toLocalDate()
            )
        );
    }

    @Override
    @Transactional
    public Integer remove(long id) {
        return jdbcTemplate.update(userQuery.getDeleteUserById(), id);
    }

    @Override
    @Transactional
    public Integer add(long id) {
        return jdbcTemplate.update(userQuery.getInsertUser(), id, Timestamp.valueOf(LocalDateTime.now()));
    }
}
