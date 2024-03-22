package edu.java.scrapper.repository.query;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class GitCommitQuery {
    private final String getCommitByUrl = "select * from gitcommits where url = ?;";
    private final String addCommit =
        "insert into gitcommits (name, made_date, url, comment_number) values (?, ?, ?, ?);";
}
