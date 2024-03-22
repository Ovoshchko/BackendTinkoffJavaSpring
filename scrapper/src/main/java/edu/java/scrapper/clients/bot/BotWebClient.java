package edu.java.scrapper.clients.bot;

import edu.java.scrapper.dto.request.LinkUpdate;
import edu.java.scrapper.dto.response.ApiErrorResponse;
import java.util.Objects;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class BotWebClient implements BotClient {

    private final WebClient webClient;

    public BotWebClient(String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public String postUpdate(LinkUpdate linkUpdate) {
        return webClient.post()
            .uri("/updates")
            .bodyValue(linkUpdate)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class)
            .onErrorResume(WebClientResponseException.class, e ->
                Mono.just(Objects.requireNonNull(e.getResponseBodyAs(ApiErrorResponse.class)).description()))
            .block();
    }
}
