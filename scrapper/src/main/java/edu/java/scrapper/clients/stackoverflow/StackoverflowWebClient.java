package edu.java.scrapper.clients.stackoverflow;

import edu.java.scrapper.dto.stackoverflow.ListAnswer;
import edu.java.scrapper.dto.stackoverflow.StackoverflowResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

public class StackoverflowWebClient implements StackoverflowClient {

    public static final String SITE_PARAM_NAME = "site";
    public static final String STACKOVERFLOW_NAME = "stackoverflow";
    private final static String ENDPOINT = "questions/{id}";
    private final static String ANSWER_ENDPOINT = "questions/{id}/answers";
    private final WebClient webClient;

    public StackoverflowWebClient(String baseUrl, ExchangeFilterFunction retryFilterFunction) {
        webClient = WebClient.builder().baseUrl(baseUrl).filter(retryFilterFunction).build();
    }

    @Override
    public StackoverflowResponse fetchUpdate(Long id) {
        return webClient.get()
            .uri(UriComponentsBuilder.fromUriString(ENDPOINT).queryParam(SITE_PARAM_NAME, STACKOVERFLOW_NAME)
                .buildAndExpand(id)
                .toUriString())
            .retrieve()
            .bodyToMono(StackoverflowResponse.class)
            .block();
    }

    @Override
    public ListAnswer checkForAnswers(Long id) {
        return webClient.get()
            .uri(UriComponentsBuilder.fromUriString(ANSWER_ENDPOINT).queryParam(SITE_PARAM_NAME, STACKOVERFLOW_NAME)
                .buildAndExpand(id)
                .toUriString())
            .retrieve()
            .bodyToMono(ListAnswer.class)
            .block();
    }

}
