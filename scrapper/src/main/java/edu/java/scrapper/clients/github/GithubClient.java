package edu.java.scrapper.clients.github;

import edu.java.scrapper.dto.github.GithubResponse;

public interface GithubClient {
    GithubResponse fetchUpdate(String user, String repo);
}
