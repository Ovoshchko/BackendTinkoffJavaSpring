package edu.java.bot.data_base_imitation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Финальный класс. Чисто как заглушка на время
public final class LinksDB {

    private final static Map<Long, List<String>> LINK_DB = new HashMap<>();

    private LinksDB() {}

    public static void addLink(long id, String link) {
        if (LINK_DB.containsKey(id)) {
            if (!LINK_DB.get(id).contains(link)) {
                LINK_DB.get(id).add(link);
            }
        } else {
            LINK_DB.put(id, new ArrayList<>());
            LINK_DB.get(id).add(link);
        }
    }

    public static void deleteLink(long id, String link) {
        if (LINK_DB.containsKey(id)) {
            LINK_DB.get(id).remove(link);
            if (LINK_DB.get(id).isEmpty()) {
                LINK_DB.remove(id);
            }
        }
    }

    public static List<String> getUsersLinks(long id) {
        return LINK_DB.get(id) == null ? new ArrayList<>() : LINK_DB.get(id);
    }

}
