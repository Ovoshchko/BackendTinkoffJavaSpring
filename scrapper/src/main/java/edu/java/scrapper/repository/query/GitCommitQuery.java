package edu.java.scrapper.repository.query;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class GitCommitQuery {
    private final String getCommitByUrl = "SELECT * FROM gitcommits WHERE url = ?;";
    private final String addCommit =
        "INSERT INTO gitcommits (name, made_date, url, comment_number) VALUES (?, ?, ?, ?);";

}
