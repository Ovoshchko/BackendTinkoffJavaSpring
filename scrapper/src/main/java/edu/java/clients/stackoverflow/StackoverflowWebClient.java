package edu.java.clients.stackoverflow;

import edu.java.dto.stackoverflow.StackoverflowResponse;
import org.springframework.web.reactive.function.client.WebClient;

public class StackoverflowWebClient implements StackoverflowClient {

    private final static String ENDPOINT = "questions/{id}?site=stackoverflow";
    private final WebClient webClient;
    private final String baseUrl = "https://api.stackexchange.com/2.3/";

    public StackoverflowWebClient() {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public StackoverflowWebClient(String baseUrl) {
        if (baseUrl.isBlank()) {
            webClient = WebClient.builder().baseUrl(this.baseUrl).build();
        } else {
            webClient = WebClient.builder().baseUrl(baseUrl).build();
        }
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
