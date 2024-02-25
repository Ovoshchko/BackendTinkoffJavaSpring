package edu.java.configuration;

import edu.java.clients.github.GithubWebClient;
import edu.java.clients.stackoverflow.StackoverflowWebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Bean
    public GithubWebClient getGithubClient() {
        return new GithubWebClient();
    }

    @Bean
    public StackoverflowWebClient getStackoverflowClient() {
        return new StackoverflowWebClient();
    }
}
