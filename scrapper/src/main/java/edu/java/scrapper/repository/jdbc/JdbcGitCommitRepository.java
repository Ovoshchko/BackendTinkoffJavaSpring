package edu.java.scrapper.repository.jdbc;

import edu.java.scrapper.dto.github.Commit;
import edu.java.scrapper.repository.GitCommitRepository;
import edu.java.scrapper.repository.query.GitCommitQuery;
import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.ZoneOffset;
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
    public Integer addCommit(Commit commit) {
        List<Commit> existingCommit = getCommitByUrl(commit.commit().url());
        if ((existingCommit != null) && (!existingCommit.isEmpty())) {
            return 1;
        }
        return jdbcTemplate.update(
            gitCommitQuery.getAddCommit(),
            commit.commit().author().name(),
            Timestamp.valueOf(commit.commit().author().date().toInstant().atZone(ZoneOffset.UTC).toLocalDateTime()),
            commit.commit().url().toString(),
            commit.commit().commentCount()
        );
    }

    @Override
    public List<Commit> getCommitByUrl(URI url) {
        return jdbcTemplate.query(
            con -> {
                PreparedStatement statement = con.prepareStatement(gitCommitQuery.getGetCommitByUrl());
                statement.setString(1, url.toString());
                return statement;
            },
            (ResultSet resultSet, int rowNum) -> new Commit(
                new Commit.CommitData(
                    new Commit.CommitData.Author(
                        resultSet.getString(NAME_NAME),
                        resultSet.getTimestamp(MADE_DATE_NAME).toLocalDateTime().atOffset(ZoneOffset.UTC)
                    ),
                    URI.create(resultSet.getString(URL_NAME)),
                    resultSet.getInt(COMMENT_NUMBER_NAME)
                )
            )
        );
    }

}
