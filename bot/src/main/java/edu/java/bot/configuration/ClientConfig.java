package edu.java.bot.configuration;

import edu.java.bot.client.scrapper.ScrapperWebClient;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
public class ClientConfig {

    @Value("${api.scrapper.baseUrl}")
    private String scrapperBasePath;

    @Bean(name = "scrapperWebClient")
    public ScrapperWebClient scrapperWebClient() {
        return new ScrapperWebClient(scrapperBasePath);
    }
}
