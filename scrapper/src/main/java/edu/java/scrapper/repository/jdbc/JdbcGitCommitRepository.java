package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.model.GitCommit;
import edu.java.scrapper.repository.GitCommitRepository;
import edu.java.scrapper.repository.query.GitCommitQuery;
import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class JdbcGitCommitRepository implements GitCommitRepository {

    private final JdbcTemplate jdbcTemplate;
    private final GitCommitQuery gitCommitQuery;

    @Override
    public Integer addCommit(GitCommit commit) {
        List<GitCommit> existingCommit = getCommitByUrl(URI.create(commit.getUrl()));
        if ((existingCommit != null) && (!existingCommit.isEmpty())) {
            return 1;
        }
        return jdbcTemplate.update(
            gitCommitQuery.getAddCommit(),
            commit.getName(),
            Timestamp.valueOf(commit.getMadeDate()),
            commit.getUrl(),
            commit.getCommentNumber()
        );
    }

    @Override
    public List<GitCommit> getCommitByUrl(URI url) {
        return jdbcTemplate.query(
            con -> {
                PreparedStatement statement = con.prepareStatement(gitCommitQuery.getGetCommitByUrl());
                statement.setString(1, url.toString());
                return statement;
            },
            (ResultSet resultSet, int rowNum) -> new GitCommit()
                .setName(resultSet.getString(NAME_NAME))
                .setMadeDate(resultSet.getTimestamp(MADE_DATE_NAME).toLocalDateTime())
                .setUrl(resultSet.getString(URL_NAME))
                .setCommentNumber(resultSet.getLong(COMMENT_NUMBER_NAME))
        );
    }

}
