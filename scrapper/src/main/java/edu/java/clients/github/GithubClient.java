package edu.java.clients.github;

import edu.java.dto.github.GithubResponse;

public interface GithubClient {
    GithubResponse fetchUpdate(String user, String repo);
}
