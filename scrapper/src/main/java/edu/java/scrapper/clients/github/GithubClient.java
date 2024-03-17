package edu.java.scrapper.clients.github;

import edu.java.scrapper.dto.github.GithubResponse;
import java.net.URI;

public interface GithubClient {
    GithubResponse fetchUpdate(String user, String repo);

    GithubResponse checkForUpdate(URI url);


}
