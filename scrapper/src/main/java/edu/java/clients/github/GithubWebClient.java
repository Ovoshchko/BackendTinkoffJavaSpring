package edu.java.clients.github;

import edu.java.dto.github.GithubResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class GithubWebClient implements GithubClient {

    private final static String ENDPOINT = "/repos/{owner}/{repo}";
    private final WebClient webClient;
    private final String baseUrl = "https://api.github.com/";

    public GithubWebClient() {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public GithubWebClient(String baseUrl) {
        if (baseUrl.isBlank()) {
            webClient = WebClient.builder().baseUrl(this.baseUrl).build();
        } else {
            webClient = WebClient.builder().baseUrl(baseUrl).build();
        }
    }

    @Override
    public GithubResponse fetchUpdate(String user, String repo) {
        return webClient.get()
            .uri(ENDPOINT, user, repo)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(GithubResponse.class)
            .block();
    }
}
