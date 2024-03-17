package edu.java.scrapper.clients.stackoverflow;

import edu.java.scrapper.dto.stackoverflow.StackoverflowResponse;
import org.springframework.web.reactive.function.client.WebClient;

public class StackoverflowWebClient implements StackoverflowClient {

    private final static String ENDPOINT = "questions/{id}?site=stackoverflow";
    private final WebClient webClient;

    public StackoverflowWebClient(String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public StackoverflowResponse fetchUpdate(Long id) {
        return webClient.get()
            .uri(ENDPOINT, id)
            .retrieve()
            .bodyToMono(StackoverflowResponse.class)
            .block();
    }
}