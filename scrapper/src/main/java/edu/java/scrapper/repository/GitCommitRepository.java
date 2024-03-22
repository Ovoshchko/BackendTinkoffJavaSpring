package edu.java.scrapper.repository;

import edu.java.scrapper.dto.github.Commit;
import java.net.URI;
import java.util.List;

public interface GitCommitRepository {
    String NAME_NAME = "name";
    String MADE_DATE_NAME = "made_date";
    String URL_NAME = "url";
    String COMMENT_NUMBER_NAME = "comment_number";

    List<Commit> getCommitByUrl(URI url);

    Integer addCommit(Commit commit);
}
