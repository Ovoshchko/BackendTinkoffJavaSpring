package edu.java.bot.data_base_imitation;

import java.util.HashMap;
import java.util.Map;

public final class UserDB {

    private static final Map<Long, String> USER_DB = new HashMap<>();

    private UserDB() {
    }

    public static void addUser(Long id, String name) {
        USER_DB.put(id, name);
    }

    public static void deleteUser(long id) {
        USER_DB.remove(id);
    }
}
