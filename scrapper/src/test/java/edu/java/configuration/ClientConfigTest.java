package edu.java.configuration;

import edu.java.clients.github.GithubWebClient;
import edu.java.clients.stackoverflow.StackoverflowWebClient;
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
