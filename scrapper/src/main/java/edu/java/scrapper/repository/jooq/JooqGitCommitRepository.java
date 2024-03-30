package edu.java.scrapper.repository.jooq;

import edu.java.scrapper.domain.jooq.linkviewer.Tables;
import edu.java.scrapper.model.GitCommit;
import edu.java.scrapper.repository.GitCommitRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import java.net.URI;
import java.util.List;
import static edu.java.scrapper.domain.jooq.linkviewer.Tables.GITCOMMITS;

@Repository
@RequiredArgsConstructor
public class JooqGitCommitRepository implements GitCommitRepository {

    private final DSLContext dsl;

    @Override
    public List<GitCommit> getCommitByUrl(URI url) {
        return dsl.select()
            .from(GITCOMMITS)
            .where(GITCOMMITS.URL.eq(url.toString()))
            .fetchInto(GitCommit.class);
    }

    @Override
    public Integer addCommit(GitCommit commit) {
        return dsl
            .insertInto(GITCOMMITS)
            .set(GITCOMMITS.NAME, commit.getName())
            .set(GITCOMMITS.URL, commit.getUrl())
            .set(
                GITCOMMITS.MADE_DATE,
                commit.getMadeDate()
            )
            .set(GITCOMMITS.COMMENT_NUMBER, (long) commit.getCommentNumber())
            .execute();
    }
}
