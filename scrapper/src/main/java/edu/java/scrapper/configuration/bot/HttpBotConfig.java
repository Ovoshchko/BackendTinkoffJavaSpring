package edu.java.scrapper.configuration.bot;

import edu.java.scrapper.clients.bot.BotClient;
import edu.java.scrapper.service.bot.BotService;
import edu.java.scrapper.service.bot.HttpBotService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class HttpBotConfig {

    @Bean
    public BotService botService(BotClient botClient) {
        return new HttpBotService(botClient);
    }
}
