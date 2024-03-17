package edu.java.scrapper.clients.github;

import edu.java.scrapper.dto.github.GithubResponse;
import java.net.URI;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class GithubWebClient implements GithubClient {

    private final static String ENDPOINT = "/repos/{owner}/{repo}";
    private final WebClient webClient;

    public GithubWebClient(String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
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

    @Override
    @SneakyThrows
    public GithubResponse checkForUpdate(URI url) {
        String[] path = url.getPath().split("/");

        String user = path[1];
        String repo = path[2];

        return fetchUpdate(user, repo);
    }
}
