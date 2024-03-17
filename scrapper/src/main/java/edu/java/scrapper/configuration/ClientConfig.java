package edu.java.scrapper.configuration;

import edu.java.scrapper.clients.bot.BotWebClient;
import edu.java.scrapper.clients.github.GithubWebClient;
import edu.java.scrapper.clients.stackoverflow.StackoverflowWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Value("${api.github.baseUrl:https://api.github.com/}")
    private String gitBasePath;
    @Value("${api.stackoverflow.baseUrl:https://api.stackexchange.com/2.3/}")
    private String stackoverflowBasePath;
    @Value("${api.bot.baseUrl:http://localhost:8090}")
    private String botBasePath;

    @Bean
    public GithubWebClient githubWebClient() {
        return new GithubWebClient(gitBasePath);
    }

    @Bean
    public StackoverflowWebClient stackoverflowWebClient() {
        return new StackoverflowWebClient(stackoverflowBasePath);
    }

    @Bean
    public BotWebClient botWebClient() {
        return new BotWebClient(botBasePath);
    }
}
