package edu.java.scrapper.repository;

import edu.java.scrapper.model.GitCommit;
import java.net.URI;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface GitCommitRepository {
    String NAME_NAME = "name";
    String MADE_DATE_NAME = "made_date";
    String URL_NAME = "url";
    String COMMENT_NUMBER_NAME = "comment_number";

    List<GitCommit> getCommitByUrl(URI url);

    @Transactional
    Integer addCommit(GitCommit commit);
}
