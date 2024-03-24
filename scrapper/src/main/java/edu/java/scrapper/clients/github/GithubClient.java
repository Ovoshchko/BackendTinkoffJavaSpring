package edu.java.scrapper.clients.github;

import edu.java.scrapper.dto.github.Commit;
import edu.java.scrapper.dto.github.GithubResponse;

public interface GithubClient {
    GithubResponse fetchUpdate(String user, String repo);

    Commit[] checkCommits(String user, String repo);

}
