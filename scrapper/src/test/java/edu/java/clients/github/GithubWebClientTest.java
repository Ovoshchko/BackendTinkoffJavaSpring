package edu.java.clients.github;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Body;
import edu.java.dto.github.GithubResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import java.time.OffsetDateTime;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
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
                "created_at": "2022-12-04T18:16:29Z"
            }
        """;

    private final static GithubResponse ANSWER = new GithubResponse(
        "ovoshch",
        new GithubResponse.Owner("Ovoshchko"),
        OffsetDateTime.parse("2022-12-04T18:16:29Z")
    );
    private final static String OWNER = "owner";
    private final static String REPO = "repo";

    private static WireMockServer wireMockServer;

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
        String baseUrl = "http://localhost:" + wireMockServer.port();

        wireMockServer.stubFor(get(urlPathEqualTo("/repos/" + OWNER + "/" + REPO))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withResponseBody(new Body(SERVER_RESPONSE))));

        GithubClient client = new GithubWebClient(baseUrl);
        GithubResponse result = client.fetchUpdate(OWNER, REPO);

        assertThat(result).isEqualTo(ANSWER);
    }
}
