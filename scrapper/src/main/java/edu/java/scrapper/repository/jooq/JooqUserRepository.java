package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.model.User;
import edu.java.scrapper.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static edu.java.scrapper.domain.jooq.linkviewer.Tables.USERS;

@Repository
@RequiredArgsConstructor
@Qualifier("jooqUserRepository")
public class JooqUserRepository implements UserRepository {

    private final DSLContext dsl;

    @Override
    public List<User> findAll() {
        return dsl.selectFrom(USERS).fetchInto(User.class);
    }

    @Override
    @Transactional
    public Integer remove(long id) {
        return dsl.deleteFrom(USERS).where(USERS.TG_ID.eq(id)).execute();
    }

    @Override
    @Transactional
    public Integer add(long id) {
        return dsl.insertInto(USERS).set(USERS.TG_ID, id)
            .set(USERS.CREATED_AT, LocalDateTime.now().atOffset(ZoneOffset.UTC)).execute();
    }
}
