package edu.java.scrapper.repository;

import edu.java.scrapper.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    User findById(long id);

    @Transactional
    Integer remove(long id);

    @Transactional
    Integer add(long id);
}
