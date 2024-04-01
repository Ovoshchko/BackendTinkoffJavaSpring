package edu.java.scrapper.clients.bot;

import edu.java.scrapper.dto.request.LinkUpdate;
import edu.java.scrapper.dto.response.ApiErrorResponse;
import edu.java.scrapper.exception.ServerUnavaliableError;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class BotWebClient implements BotClient {

    public static final String UNKNOWN_ERROR = "Unknown error";
    public static final String UPDATES_ENDPOINT = "/updates";
    private final WebClient webClient;

    public BotWebClient(String baseUrl, ExchangeFilterFunction retryFilterFunction) {
        webClient = WebClient.builder().baseUrl(baseUrl).filter(retryFilterFunction).build();
    }

    @Override
    public String postUpdate(LinkUpdate linkUpdate) {
        return webClient.post()
            .uri(UPDATES_ENDPOINT)
            .bodyValue(linkUpdate)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class)
            .onErrorResume(WebClientResponseException.class, e -> {
                ApiErrorResponse errorResponse = e.getResponseBodyAs(ApiErrorResponse.class);
                return Mono.just(errorResponse != null ? errorResponse.description() : UNKNOWN_ERROR);
            })
            .onErrorResume(ServerUnavaliableError.class, e ->
                Mono.just(e.getMessage()))
            .block();
    }
}
