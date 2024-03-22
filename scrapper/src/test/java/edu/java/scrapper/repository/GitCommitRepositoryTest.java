package edu.java.scrapper.repository;

import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.dto.github.Commit;
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
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class GitCommitRepositoryTest extends IntegrationTest {

    private static final Commit COMMIT = new Commit(new Commit.CommitData(
        new Commit.CommitData.Author(
            "Ovoshchko",
            OffsetDateTime.parse("2022-12-04T18:16:29+03:00")
        ),
        URI.create("https://example.com"),
        0
    ));
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
            statement.setString(1, COMMIT.commit().author().name());
            statement.setTimestamp(
                2,
                Timestamp.valueOf(COMMIT.commit().author().date().toLocalDateTime()
                    .atOffset(ZoneOffset.systemDefault().getRules()
                        .getOffset(Instant.now())).toLocalDateTime())
            );
            statement.setString(3, COMMIT.commit().url().toString());
            statement.setInt(4, COMMIT.commit().commentCount());
            return statement;
        });

        for (GitCommitRepository gitCommitRepository : gitCommitRepositories) {
            List<Commit> commitList = gitCommitRepository.getCommitByUrl(COMMIT.commit().url());

            assertEquals(1, commitList.size());
            assertThat(commitList.get(0).commit().author().name()).isEqualTo(COMMIT.commit().author().name());
            assertTrue(commitList.get(0).commit().author().date().equals(COMMIT.commit().author().date()));
        }
    }

    @Test
    @Transactional
    @Rollback
    void addCommit() {

        for (GitCommitRepository gitCommitRepository : gitCommitRepositories) {
            jdbcTemplate.update("DELETE FROM gitcommits;");

            gitCommitRepository.addCommit(COMMIT);

            List<Commit> commitList = jdbcTemplate.query(
                "SELECT * FROM gitcommits;",
                (ResultSet resultSet, int rowNum) ->
                    new Commit(
                        new Commit.CommitData(
                            new Commit.CommitData.Author(
                                resultSet.getString("name"),
                                resultSet.getTimestamp("made_date").toInstant()
                                    .atOffset(ZoneOffset.systemDefault().getRules()
                                        .getOffset(Instant.now()))
                            ),
                            URI.create(resultSet.getString("url")),
                            resultSet.getInt("comment_number")
                        )
                    )
            );

            assertEquals(1, commitList.size());
            assertThat(commitList.get(0).commit().author()).isEqualTo(COMMIT.commit().author());
            assertTrue(commitList.get(0).commit().author().date().equals(COMMIT.commit().author().date()));
        }
    }
}
