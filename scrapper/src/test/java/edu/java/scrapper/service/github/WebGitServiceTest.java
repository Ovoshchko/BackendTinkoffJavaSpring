package edu.java.scrapper.service.github;

import edu.java.scrapper.clients.github.GithubWebClient;
import edu.java.scrapper.dto.github.Commit;
import edu.java.scrapper.dto.github.GithubResponse;
import edu.java.scrapper.repository.GitCommitRepository;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import edu.java.scrapper.repository.jdbc.JdbcGitCommitRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WebGitServiceTest {

    @Mock
    private GithubWebClient githubClient;
    @Mock
    private JdbcGitCommitRepository jdbcGitCommitRepository;
    @InjectMocks
    private WebGitService webGitService;

    private static Stream<Arguments> provideArgs() {
        return Stream.of(
            Arguments.of(
                URI.create("https://github.com/Ovoshchko/C_course"),
                LocalDateTime.now(),
                new GithubResponse(
                    "C_course",
                    new GithubResponse.Owner("Ovoshch"),
                    OffsetDateTime.now().minusMinutes(5),
                    OffsetDateTime.now().minusMinutes(5)
                ),
                new Commit[] {},
                new ArrayList<>()
            ),
            Arguments.of(
                URI.create("https://github.com/Ovoshchko/C_course"),
                LocalDateTime.now().minusMinutes(5),
                new GithubResponse(
                    "C_course",
                    new GithubResponse.Owner("Ovoshch"),
                    OffsetDateTime.now().minusMinutes(5),
                    OffsetDateTime.now()
                ),
                new Commit[] {
                    new Commit(new Commit.CommitData(
                        new Commit.CommitData.Author("Ovoshch", OffsetDateTime.now()),
                        URI.create(
                            "https://github.com/Ovoshchko/C_course/commit/32e51b60c0377daf6fb1d1b3e287161592dbf54c"),
                        3
                    ))},
                List.of(
                    "https://github.com/Ovoshchko/C_course/commit/32e51b60c0377daf6fb1d1b3e287161592dbf54c Появился новый коммит!" +
                        System.lineSeparator())
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgs")
    void checkForUpdates(
        URI url,
        LocalDateTime time,
        GithubResponse response,
        Commit[] commits,
        List<String> description
    ) {
        String[] paths = url.getPath().split("/");

        String owner = paths[1];
        String repo = paths[2];

        when(githubClient.fetchUpdate(owner, repo)).thenReturn(response);
        if (commits.length != 0) {
            when(githubClient.checkCommits(owner, repo)).thenReturn(commits);
        }

        List<String> serviceResponse = webGitService.checkForUpdates(url, time);

        assertThat(serviceResponse).isEqualTo(description);
    }
}
