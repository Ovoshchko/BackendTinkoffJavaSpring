package edu.java.scrapper.service.bot;

import edu.java.scrapper.clients.bot.BotClient;
import edu.java.scrapper.dto.request.LinkUpdate;
import lombok.Data;

@Data
public class HttpBotService implements BotService {

    private final BotClient botClient;

    public String postUpdate(LinkUpdate linkUpdate) {
        return botClient.postUpdate(linkUpdate);
    }
}
