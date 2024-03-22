package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.domain.jooq.linkviewer.Tables;
import edu.java.scrapper.domain.jooq.linkviewer.tables.records.GitcommitsRecord;
import edu.java.scrapper.dto.github.Commit;
import edu.java.scrapper.repository.GitCommitRepository;
import java.net.URI;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JooqGitCommitRepository implements GitCommitRepository {

    private final DSLContext dsl;

    @Override
    public List<Commit> getCommitByUrl(URI url) {
        return dsl.select()
            .from(Tables.GITCOMMITS)
            .where(Tables.GITCOMMITS.URL.eq(url.toString()))
            .fetchInto(GitcommitsRecord.class)
            .stream()
            .map((GitcommitsRecord commitRecord) -> new Commit(
                new Commit.CommitData(
                    new Commit.CommitData.Author(
                        commitRecord.getName(),
                        commitRecord.getMadeDate().atZone(ZoneOffset.UTC).toOffsetDateTime()
                    ),
                    URI.create(commitRecord.getUrl()),
                    commitRecord.getCommentNumber().intValue()
                )
            ))
            .collect(Collectors.toList());
    }

    @Override
    public Integer addCommit(Commit commit) {
        return dsl
            .insertInto(Tables.GITCOMMITS)
            .set(Tables.GITCOMMITS.NAME, commit.commit().author().name())
            .set(Tables.GITCOMMITS.URL, commit.commit().url().toString())
            .set(
                Tables.GITCOMMITS.MADE_DATE,
                commit.commit().author().date().toInstant().atZone(ZoneOffset.UTC).toLocalDateTime()
            )
            .set(Tables.GITCOMMITS.COMMENT_NUMBER, (long) commit.commit().commentCount())
            .execute();
    }
}
