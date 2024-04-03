package edu.java.scrapper.service.github;

import edu.java.scrapper.clients.github.GithubClient;
import edu.java.scrapper.dto.github.Commit;
import edu.java.scrapper.dto.github.GithubResponse;
import edu.java.scrapper.model.GitCommit;
import edu.java.scrapper.repository.GitCommitRepository;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class WebGitService implements GitService {

    private final GithubClient githubClient;
    private final GitCommitRepository gitCommitRepository;

    @Override
    public List<String> checkForUpdates(URI url, LocalDateTime time) {
        List<String> description = new ArrayList<>();
        try {

            String[] pathComponents = url.getPath().split("/");

            String user = pathComponents[1];
            String repo = pathComponents[2];

            GithubResponse githubResponse = githubClient.fetchUpdate(user, repo);

            if (time.isBefore(githubResponse.lastUpdateTime().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime())) {
                description = List.of(url + " " + UNKNOWN_UPDATE);

                Commit[] commits = githubClient.checkCommits(user, repo);

                if ((commits != null) && (commits.length > 0)) {
                    description = processCommits(commits);
                }

                if (description.isEmpty()) {
                    description = List.of(url + " " + NOT_COMMIT_UPDATE);
                }
            }

        } catch (ArrayIndexOutOfBoundsException ignoreException) {
            description = List.of(url + " " + BAD_LINK);
        }

        return description;
    }

    private List<String> processCommits(Commit[] commits) {

        List<String> stringCommits = new ArrayList<>();
        for (Commit commit : commits) {
            List<GitCommit> existingCommit = gitCommitRepository.getCommitByUrl(commit.commit().url());
            if (existingCommit.isEmpty()) {
                gitCommitRepository.addCommit(new GitCommit().setName(commit.commit().author().name())
                    .setMadeDate(commit.commit().author().date().toLocalDateTime())
                    .setUrl(commit.commit().url().toString())
                    .setCommentNumber((long) commit.commit().commentCount()));
                stringCommits.add(commit.commit().url() + " " + NEW_COMMIT + System.lineSeparator());
            } else if (existingCommit.get(0).getCommentNumber() < commit.commit().commentCount()) {
                stringCommits.add(commit.commit().url().toString() + " " + NEW_COMMENTS_UPDATE
                    + (commit.commit().commentCount() - existingCommit.get(0).getCommentNumber())
                    + System.lineSeparator());
            }
        }

        return stringCommits;
    }
}
