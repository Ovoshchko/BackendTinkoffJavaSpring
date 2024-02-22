package edu.java.bot.data_base_imitation;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class UserDB {

    private final Map<Long, String> userDb;

    public UserDB() {
        userDb = new HashMap<>();
    }

    public void addUser(Long id, String name) {
        userDb.put(id, name);
    }

    public void deleteUser(long id) {
        userDb.remove(id);
    }
}
