package edu.java.scrapper.repository.query;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class UserQuery {
    private final String selectAllUsers = "SELECT * FROM users;";
    private final String deleteUserById = "DELETE FROM users WHERE tg_id = ?";
    private final String insertUser = "INSERT INTO users VALUES (?, ?);";
    private final String findByIdUser = "SELECT * FROM users WHERE tg_id = ?";
}
