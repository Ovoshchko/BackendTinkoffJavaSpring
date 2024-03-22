package edu.java.scrapper.repository.query;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class UserLinkQuery {
    private final String insertIntoUserlink = "INSERT INTO userlink VALUES (?,?);";
    private final String deleteFromUserlink = "DELETE FROM userlink WHERE (user_id = ?) AND (link_id = ?);";
    private final String selectAllLinksByUser =
        "SELECT * FROM links l JOIN userlink ul ON ul.link_id = l.id WHERE ul.user_id = ?;";
    private final String selectUsersByLink =
        "SELECT user_id FROM userlink WHERE link_id = ?;";

}
