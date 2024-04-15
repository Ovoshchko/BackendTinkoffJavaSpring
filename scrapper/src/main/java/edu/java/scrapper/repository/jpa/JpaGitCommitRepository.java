package edu.java.scrapper.repository.jpa;

import edu.java.scrapper.model.GitCommit;
import edu.java.scrapper.repository.GitCommitRepository;
import edu.java.scrapper.repository.jpa.dao.GitCommitDao;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class JpaGitCommitRepository implements GitCommitRepository {

    private final GitCommitDao gitCommitDao;

    @Override
    public List<GitCommit> getCommitByUrl(URI url) {
        return gitCommitDao.findAllById(List.of(url.toString()));
    }

    @Override
    public Integer addCommit(GitCommit commit) {
        gitCommitDao.saveAndFlush(commit);
        return 1;
    }
}
