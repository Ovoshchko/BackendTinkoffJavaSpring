package edu.java.scrapper.service.github;

import edu.java.scrapper.clients.github.GithubClient;
import edu.java.scrapper.dto.github.Commit;
import edu.java.scrapper.dto.github.GithubResponse;
import edu.java.scrapper.model.GitCommit;
import edu.java.scrapper.repository.GitCommitRepository;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebGitService implements GitService {

    public static final String UNKNOWN_UPDATE = "Пришло обновление, но я не знаю какое(";
    public static final String NOT_COMMIT_UPDATE = "Что-то произошло. Но это было не в коммитах";
    public static final String NEW_COMMIT = "Появился новый коммит!";
    public static final String NEW_COMMENTS_UPDATE = "Появились новые комменты. Количество: ";
    public static final String BAD_LINK = "Плохая ссылка";
    private final GithubClient githubClient;
    private final GitCommitRepository jdbcGitCommitRepository;

    @Override
    public List<String> checkForUpdates(URI url, LocalDateTime time) {
        List<String> description = new ArrayList<>();
        try {

            String[] pathComponents = url.getPath().split("/");

            String user = pathComponents[1];
            String repo = pathComponents[2];

            GithubResponse githubResponse = githubClient.fetchUpdate(user, repo);

            if (time.isBefore(githubResponse.lastUpdateTime().toLocalDateTime())) {
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
            List<GitCommit> existingCommit = jdbcGitCommitRepository.getCommitByUrl(commit.commit().url());
            if (existingCommit.isEmpty()) {
                jdbcGitCommitRepository.addCommit(new GitCommit().setName(commit.commit().author().name())
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
