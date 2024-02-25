package edu.java.configuration;

import edu.java.clients.github.GithubClient;
import edu.java.clients.github.GithubWebClient;
import edu.java.clients.stackoverflow.StackoverflowClient;
import edu.java.clients.stackoverflow.StackoverflowWebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Bean
    public GithubClient getGithubClient() {
        return new GithubWebClient();
    }

    @Bean
    public StackoverflowClient getStackoverflowClient() {
        return new StackoverflowWebClient();
    }
}
