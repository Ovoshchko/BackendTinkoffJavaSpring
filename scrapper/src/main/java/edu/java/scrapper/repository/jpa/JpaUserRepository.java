package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.model.User;
import edu.java.scrapper.repository.UserRepository;
import edu.java.scrapper.repository.jpa.dao.UserDao;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {

    private final UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(long id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public Integer remove(long id) {
        userDao.deleteById(id);
        userDao.flush();
        return 1;
    }

    @Override
    public Integer add(long id) {
        userDao.saveAndFlush(new User().setTgId(id).setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC).toLocalDateTime()));
        return 1;
    }
}
