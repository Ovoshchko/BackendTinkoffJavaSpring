package edu.java.bot.configuration;

import edu.java.bot.client.scrapper.ScrapperWebClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Value("${api.scrapper.baseUrl}")
    private String scrapperBasePath;

    @Bean(name = "scrapperWebClient")
    public ScrapperWebClient scrapperWebClient() {
        return new ScrapperWebClient(scrapperBasePath);
    }
}
