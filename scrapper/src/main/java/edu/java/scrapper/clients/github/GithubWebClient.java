package edu.java.scrapper.clients.github;

import edu.java.scrapper.dto.github.Commit;
import edu.java.scrapper.dto.github.GithubResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class GithubWebClient implements GithubClient {

    private final static String REPO_ENDPOINT = "/repos/{owner}/{repo}";
    private final static String COMMITS_ENDPOINT = REPO_ENDPOINT + "/commits";
    private final WebClient webClient;

    public GithubWebClient(String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public GithubResponse fetchUpdate(String user, String repo) {
        return webClient.get()
            .uri(REPO_ENDPOINT, user, repo)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(GithubResponse.class)
            .block();
    }

    @Override
    public Commit[] checkCommits(String user, String repo) {

        return webClient.get()
            .uri(COMMITS_ENDPOINT, user, repo)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Commit[].class)
            .block();
    }
}
