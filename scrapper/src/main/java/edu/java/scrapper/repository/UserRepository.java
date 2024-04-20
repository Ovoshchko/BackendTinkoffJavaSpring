package edu.java.scrapper.repository;

import edu.java.scrapper.model.User;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository {
    List<User> findAll();

    User findById(long id);

    @Transactional
    Integer remove(long id);

    @Transactional
    Integer add(long id);
}
