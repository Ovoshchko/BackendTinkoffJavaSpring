package edu.java.scrapper.repository;

import edu.java.scrapper.model.User;
import java.util.List;

public interface UserRepository {
    List<User> findAll();

    Integer remove(long id);

    Integer add(long id);
}
