package edu.java.scrapper.clients.github;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Body;
import edu.java.scrapper.dto.github.GithubResponse;
import java.net.URI;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

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

    private final static GithubResponse ANSWER = new GithubResponse(
        "ovoshch",
        new GithubResponse.Owner("Ovoshchko"),
        OffsetDateTime.parse("2022-12-04T18:16:29Z"),
        OffsetDateTime.parse("2022-12-04T18:16:29Z")
    );
    private final static String OWNER = "owner";
    private final static String REPO = "repo";
    public static final URI GITHUB_URL = URI.create("http://github.com/" + OWNER + "/" + REPO);
    public static final String HTTP_LOCALHOST = "http://localhost:";

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void init() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
    }

    @BeforeEach
    void setInit() {
        wireMockServer.stubFor(get(urlPathEqualTo("/repos/" + OWNER + "/" + REPO))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withResponseBody(new Body(SERVER_RESPONSE))));
    }

    @AfterAll
    static void close() {
        wireMockServer.stop();
    }

    @Test
    void fetchUpdate() {
        String baseUrl = HTTP_LOCALHOST + wireMockServer.port();

        GithubClient client = new GithubWebClient(baseUrl);
        GithubResponse result = client.fetchUpdate(OWNER, REPO);

        assertThat(result).isEqualTo(ANSWER);
    }

    @Test
    void checkForUpdate() {

        String baseUrl = HTTP_LOCALHOST + wireMockServer.port();

        GithubClient client = new GithubWebClient(baseUrl);
        GithubResponse response = client.checkForUpdate(GITHUB_URL);

        assertThat(response).isEqualTo(ANSWER);
    }
}
