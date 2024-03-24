package edu.java.scrapper.clients.stackoverflow;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Body;
import edu.java.scrapper.dto.stackoverflow.Answer;
import edu.java.scrapper.dto.stackoverflow.ListAnswer;
import edu.java.scrapper.dto.stackoverflow.StackoverflowResponse;
import java.net.URI;
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

    private final static String ANSWER_JSON = """
        {
            "items": [
                {
                    "owner": {
                        "account_id": 1222065,
                        "reputation": 133,
                        "user_id": 1188515,
                        "user_type": "registered",
                        "profile_image": "https://www.gravatar.com/avatar/3bfddfd9693618e41245153f2de2ec70?s=256&d=identicon&r=PG",
                        "display_name": "Pankaj Patel",
                        "link": "https://stackoverflow.com/users/1188515/pankaj-patel"
                    },
                    "is_accepted": false,
                    "score": 0,
                    "last_activity_date": 1535232298,
                    "last_edit_date": 1535232298,
                    "creation_date": 1535230721,
                    "answer_id": 52021173,
                    "question_id": 52020732,
                    "content_license": "CC BY-SA 4.0"
                }
            ],
            "has_more": false,
            "quota_max": 300,
            "quota_remaining": 266
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

    private final static ListAnswer ANSWER_ANSWER = new ListAnswer(
        List.of(
            new Answer(
                new Answer.Owner("Pankaj Patel"),
                52021173,
                52020732
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
        wireMockServer.stubFor(get(urlPathEqualTo("/questions/" + ID))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withResponseBody(new Body(SERVER_RESPONSE))));

        String baseUrl = "http://localhost:" + wireMockServer.port();

        StackoverflowClient client = new StackoverflowWebClient(baseUrl);
        StackoverflowResponse result = client.fetchUpdate(ID);

        assertThat(result).isEqualTo(ANSWER);
    }

    @Test
    void checkForAnswers() {
        wireMockServer.stubFor(get(urlPathEqualTo("/questions/" + ID + "/answers"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withResponseBody(new Body(ANSWER_JSON))));

        String baseUrl = "http://localhost:" + wireMockServer.port();

        StackoverflowClient client = new StackoverflowWebClient(baseUrl);
        ListAnswer response = client.checkForAnswers(ID);

        assertThat(response).isEqualTo(ANSWER_ANSWER);
    }

}
