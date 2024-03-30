package edu.java.scrapper.repository;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.model.GitCommit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GitCommitRepositoryTest extends IntegrationTest {

    public static final String URL = "https://example.com";
    public static final String NAME = "Ovoshchko";
    public static final String TIME = "2022-12-04T18:16:29z";
    private static final GitCommit GIT_COMMIT = new GitCommit()
        .setName(NAME)
        .setMadeDate(OffsetDateTime.parse(TIME).toLocalDateTime())
        .setUrl(URL)
        .setCommentNumber(0L);
    @Autowired
    private List<GitCommitRepository> gitCommitRepositories;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Rollback
    void getCommitByUrl() {
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(
                "insert into gitcommits (name, made_date, url, comment_number) values (?, ?, ?, ?);");
            statement.setString(1, GIT_COMMIT.getName());
            statement.setTimestamp(2, Timestamp.valueOf(GIT_COMMIT.getMadeDate()));
            statement.setString(3, GIT_COMMIT.getUrl());
            statement.setLong(4, GIT_COMMIT.getCommentNumber());
            return statement;
        });

        for (GitCommitRepository gitCommitRepository : gitCommitRepositories) {
            List<GitCommit> commitList = gitCommitRepository.getCommitByUrl(URI.create(GIT_COMMIT.getUrl()));

            assertEquals(1, commitList.size());
            assertThat(commitList.get(0).getName()).isEqualTo(GIT_COMMIT.getName());
            assertTrue(commitList.get(0).getMadeDate().equals(GIT_COMMIT.getMadeDate()));
        }
    }

    @Test
    @Transactional
    @Rollback
    void addCommit() {

        for (GitCommitRepository gitCommitRepository : gitCommitRepositories) {
            jdbcTemplate.update("DELETE FROM gitcommits;");

            gitCommitRepository.addCommit(GIT_COMMIT);

            List<GitCommit> commitList = jdbcTemplate.query(
                "SELECT * FROM gitcommits;",
                (ResultSet resultSet, int rowNum) -> new GitCommit()
                    .setName(resultSet.getString("name"))
                    .setMadeDate(resultSet.getTimestamp("made_date").toLocalDateTime())
                    .setUrl(resultSet.getString("url"))
                    .setCommentNumber(resultSet.getLong("comment_number"))
            );

            assertEquals(1, commitList.size());
            assertThat(commitList.get(0).getName()).isEqualTo(GIT_COMMIT.getName());
            assertTrue(commitList.get(0).getMadeDate().equals(GIT_COMMIT.getMadeDate()));
        }
    }
}
