package edu.java.scrapper.clients.bot;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.scrapper.configuration.retry.RetryClientConfig;
import edu.java.scrapper.configuration.retry.backoff.ConstantRetryBackoff;
import edu.java.scrapper.configuration.retry.retryparams.RetryParams;
import edu.java.scrapper.dto.request.LinkUpdate;
import java.net.URI;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BotWebClientTest {

    public static final String UPDATES_ENDPOINT = "/updates";
    public static final String SUCCESS_ANSWER = "Success";
    private static final LinkUpdate LINK_UPDATE =
        new LinkUpdate(1L, URI.create("https://ok.com"), List.of("ok"), List.of(1L, 2L));
    private static final String INTERNAL_ERROR_ANSWER = "У севера проблемы. Попробуйте попозже.";
    private static final int MAX_ATTEMPTS = 5;
    private static final String LOCALHOST = "localhost";
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
        WireMock.configureFor(LOCALHOST, wireMockServer.port());
    }

    @AfterAll
    static void stop() {
        wireMockServer.stop();
    }

    @Test
    public void postUpdateReturnsSuccess() {
        wireMockServer.stubFor(post(urlEqualTo(UPDATES_ENDPOINT))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(SUCCESS_ANSWER)));

        BotWebClient botWebClient = new BotWebClient(wireMockServer.baseUrl(), retryFilterFunction);

        String result = botWebClient.postUpdate(LINK_UPDATE);

        assertEquals(SUCCESS_ANSWER, result);
    }

    @Test
    public void postUpdate_ReturnsErrorDescription_WhenUpdateFails() {
        wireMockServer.stubFor(post(urlEqualTo(UPDATES_ENDPOINT))
            .willReturn(aResponse()
                .withStatus(400)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody("{\"description\":\"Bad Request\"}")));

        BotWebClient botWebClient = new BotWebClient(wireMockServer.baseUrl(), retryFilterFunction);

        String result = botWebClient.postUpdate(LINK_UPDATE);

        assertEquals("Bad Request", result);
    }

    @Test
    public void postUpdate_SeveralRequests() {
        wireMockServer.stubFor(post(urlEqualTo(UPDATES_ENDPOINT))
            .willReturn(aResponse()
                .withStatus(500)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(" ")));

        BotWebClient botWebClient = new BotWebClient(wireMockServer.baseUrl(), retryFilterFunction);

        String result = botWebClient.postUpdate(LINK_UPDATE);

        assertEquals(INTERNAL_ERROR_ANSWER, result);
        verify(WireMock.moreThan(MAX_ATTEMPTS - 1), postRequestedFor(urlEqualTo(UPDATES_ENDPOINT)));
    }
}

