package edu.java.bot.data_base_imitation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class LinksDB {

    private final Map<Long, List<String>> linkDb;

    public LinksDB() {
        linkDb = new HashMap<>();
    }

    public void addLink(long id, String link) {
        if (linkDb.containsKey(id)) {
            if (!linkDb.get(id).contains(link)) {
                linkDb.get(id).add(link);
            }
        } else {
            linkDb.put(id, new ArrayList<>());
            linkDb.get(id).add(link);
        }
    }

    public void deleteLink(long id, String link) {
        if (linkDb.containsKey(id)) {
            linkDb.get(id).remove(link);
            if (linkDb.get(id).isEmpty()) {
                linkDb.remove(id);
            }
        }
    }

    public List<String> getUsersLinks(long id) {
        return linkDb.get(id) == null ? new ArrayList<>() : linkDb.get(id);
    }

}
