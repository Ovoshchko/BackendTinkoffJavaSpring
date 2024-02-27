package edu.java.configuration;

import edu.java.clients.github.GithubWebClient;
import edu.java.clients.stackoverflow.StackoverflowWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Value("${api.github.baseUrl:https://api.github.com/")
    private String gitBasePath;
    @Value("${api.stackoverflow.baseUrl:https://api.stackexchange.com/2.3/")
    private String stackoverflowBasePath;

    @Bean
    public GithubWebClient getGithubClient() {
        return new GithubWebClient(gitBasePath);
    }

    @Bean
    public StackoverflowWebClient getStackoverflowClient() {
        return new StackoverflowWebClient(stackoverflowBasePath);
    }
}
