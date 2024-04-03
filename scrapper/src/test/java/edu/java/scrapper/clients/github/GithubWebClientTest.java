package edu.java.scrapper.clients.github;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Body;
import edu.java.scrapper.configuration.retry.RetryClientConfig;
import edu.java.scrapper.configuration.retry.backoff.ConstantRetryBackoff;
import edu.java.scrapper.configuration.retry.retryparams.RetryParams;
import edu.java.scrapper.dto.github.Commit;
import edu.java.scrapper.dto.github.GithubResponse;
import edu.java.scrapper.exception.ServerUnavaliableError;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GithubWebClientTest {

    private final static String SERVER_RESPONSE = """
        {
                "id": 3456,
                "name": "ovoshch",
                "private": false,
                "owner": {
                    "login": "Ovoshchko",
                    "id": 345567
                },
                "deployments_url": "https://api.github.com/repos/Ovoshchko/C_course/deployments",
                "created_at": "2022-12-04T18:16:29Z",
                "updated_at": "2022-12-04T18:16:29Z"
            }
        """;

    private static final String COMMIT_JSON = """
        [
            {
                "commit": {
                    "author": {
                        "name": "owner",
                        "date": "2022-12-04T18:16:29Z"
                    },
                    "url": "http://github.com/owner/repo",
                    "comment_count": 0
                }
            }
        ]
        """;

    private final static GithubResponse ANSWER = new GithubResponse(
        "ovoshch",
        new GithubResponse.Owner("Ovoshchko"),
        OffsetDateTime.parse("2022-12-04T18:16:29Z"),
        OffsetDateTime.parse("2022-12-04T18:16:29Z")
    );
    private final static String OWNER = "owner";
    private final static String REPO = "repo";
    private static final URI GITHUB_URL = URI.create("http://github.com/" + OWNER + "/" + REPO);
    private final static Commit[] COMMITS = new Commit[] {
        new Commit(new Commit.CommitData(new Commit.CommitData.Author(
            OWNER,
            OffsetDateTime.parse("2022-12-04T18:16:29Z")
        ), GITHUB_URL, 0))};
    private static final String HTTP_LOCALHOST = "http://localhost:";
    private static final int MAX_ATTEMPTS = 5;
    public static final String REPOS_PATH = "/repos/";
    public static final String COMMITS_ENDPOINT = "/commits";
    private static WireMockServer wireMockServer;
    private final ExchangeFilterFunction retryFilterFunction = new RetryClientConfig.RetryExchangeFilterFunction(
        MAX_ATTEMPTS,
        new RetryParams(
            new ConstantRetryBackoff(Duration.ofSeconds(1L)),
            new HashSet<>(List.of(HttpStatus.INTERNAL_SERVER_ERROR))
        )
    );

    @BeforeAll
    static void init() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
    }

    @AfterAll
    static void close() {
        wireMockServer.stop();
    }

    @Test
    void fetchUpdate() {
        wireMockServer.stubFor(get(urlPathEqualTo(REPOS_PATH + OWNER + "/" + REPO))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withResponseBody(new Body(SERVER_RESPONSE))));

        String baseUrl = HTTP_LOCALHOST + wireMockServer.port();

        GithubClient client = new GithubWebClient(baseUrl, retryFilterFunction);
        GithubResponse result = client.fetchUpdate(OWNER, REPO);

        assertThat(result).isEqualTo(ANSWER);
    }

    @Test
    void checkForCommits() {
        wireMockServer.stubFor(get(urlPathEqualTo(REPOS_PATH + OWNER + "/" + REPO + COMMITS_ENDPOINT))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withResponseBody(new Body(COMMIT_JSON))));

        String baseUrl = HTTP_LOCALHOST + wireMockServer.port();

        GithubClient client = new GithubWebClient(baseUrl, retryFilterFunction);
        Commit[] response = client.checkCommits(OWNER, REPO);

        assertThat(response).isEqualTo(COMMITS);
    }

    @Test
    void checkServerError() {
        wireMockServer.stubFor(get(urlPathEqualTo(REPOS_PATH + OWNER + "/" + REPO + COMMITS_ENDPOINT))
            .willReturn(aResponse()
                .withStatus(500)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withResponseBody(new Body(""))));

        String baseUrl = HTTP_LOCALHOST + wireMockServer.port();

        GithubClient client = new GithubWebClient(baseUrl, retryFilterFunction);

        assertThrows(ServerUnavaliableError.class, () -> client.checkCommits(OWNER, REPO));

        verify(
            WireMock.moreThan(MAX_ATTEMPTS - 1),
            getRequestedFor(urlEqualTo(REPOS_PATH + OWNER + "/" + REPO + COMMITS_ENDPOINT))
        );

    }
}
