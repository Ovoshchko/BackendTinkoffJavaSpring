package edu.java.scrapper.clients.bot;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import edu.java.scrapper.dto.request.LinkUpdate;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.MediaType;
import java.util.List;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BotWebClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void postUpdateReturnsSuccess() {
        stubFor(post(urlEqualTo("/updates"))
            .withRequestBody(equalToJson("""
                {
                    "id": 1,
                    "url": "https://ok.com",
                    "description": "ok",
                    "tgChatIds": [1, 2]
                }
                """))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("Success")));

        BotWebClient botWebClient = new BotWebClient(wireMockRule.baseUrl());

        LinkUpdate linkUpdate = new LinkUpdate(1L, "https://ok.com", "ok", List.of(1L, 2L));
        String result = botWebClient.postUpdate(linkUpdate);

        assertEquals("Success", result);
    }

    @Test
    public void postUpdate_ReturnsErrorDescription_WhenUpdateFails() {
        stubFor(post(urlEqualTo("/updates"))
            .willReturn(aResponse()
                .withStatus(400)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("{\"description\":\"Bad Request\"}")));

        BotWebClient botWebClient = new BotWebClient(wireMockRule.baseUrl());

        LinkUpdate linkUpdate = new LinkUpdate(1L, "https://ok.com", "ok", List.of(1L, 2L));
        String result = botWebClient.postUpdate(linkUpdate);

        assertEquals("Bad Request", result);
    }
}

