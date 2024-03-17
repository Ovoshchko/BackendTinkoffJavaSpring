package edu.java.scrapper.clients.stackoverflow;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Body;
import edu.java.scrapper.dto.stackoverflow.StackoverflowResponse;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

class StackoverflowWebClientTest {
    private final static String SERVER_RESPONSE = """
        {
            "items": [
                {
                    "owner": {
                        "account_id": 25572608,
                        "reputation": 1,
                        "user_id": 19352703,
                        "user_type": "registered",
                        "profile_image": "https://lh3.googleusercontent.com/a/AATXAJxGsmgxSpTJLMeaHAbA90u9ubcEyIyw3aAZdJu5=k-s256",
                        "display_name": "Saksham Behl",
                        "link": "https://stackoverflow.com/users/19352703/saksham-behl"
                    },
                    "is_answered": false,
                    "view_count": 1116,
                    "answer_count": 1,
                    "score": 0,
                    "last_activity_date": 1655400524,
                    "creation_date": 1655388975,
                    "last_edit_date": 1655391859,
                    "question_id": 72647357,
                    "link": "https://stackoverflow.com/questions/72647357/dealing-with-multiple-dtos-while-making-web-client-post-request-in-spring-boot",
                    "title": "Dealing with multiple DTO's while making web client post request in spring Boot"
                }
            ]
        }
        """;

    private final static StackoverflowResponse ANSWER = new StackoverflowResponse(
        List.of(
            new StackoverflowResponse.QuestionResponse(
                72647357L,
                "https://stackoverflow.com/questions/72647357/dealing-with-multiple-dtos-while-making-web-client-post-request-in-spring-boot",
                OffsetDateTime.parse("2022-06-16T14:16:15Z"),
                OffsetDateTime.parse("2022-06-16T17:28:44Z")
            )
        )
    );
    private final static Long ID = 72647357L;

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

        wireMockServer.stubFor(get(urlPathEqualTo("/questions/" + ID))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withResponseBody(new Body(SERVER_RESPONSE))));

        StackoverflowClient client = new StackoverflowWebClient(baseUrl);
        StackoverflowResponse result = client.fetchUpdate(ID);

        assertThat(result).isEqualTo(ANSWER);
    }

}
