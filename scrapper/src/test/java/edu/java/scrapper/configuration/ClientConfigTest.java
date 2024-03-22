package edu.java.scrapper.configuration;

import edu.java.scrapper.clients.github.GithubWebClient;
import edu.java.scrapper.clients.stackoverflow.StackoverflowWebClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ClientConfigTest {

    @Autowired
    private GithubWebClient githubWebClient;

    @Autowired
    private StackoverflowWebClient stackoverflowWebClient;

    @Test
    void getGithubClient() {
        assertNotNull(githubWebClient);
    }

    @Test
    void getStackoverflowClient() {
        assertNotNull(stackoverflowWebClient);
    }
}
