package edu.java.scrapper.repository.query;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class LinksQuery {
    private final String selectLinkIdByUrl = "SELECT id FROM links WHERE url = ?;";
    private final String insertLink = "INSERT INTO links (url, last_check) VALUES (?, ?);";
    private final String selectAllLinks = "SELECT * FROM links;";
    private final String selectLinksByLastCheckBefore = "SELECT * FROM links WHERE last_check < ?;";
    private final String deleteFromLinks = "DELETE FROM links WHERE id = ?;";
}
