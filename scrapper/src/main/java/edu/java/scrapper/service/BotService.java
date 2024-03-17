package edu.java.scrapper.service;

import edu.java.scrapper.clients.bot.BotWebClient;
import edu.java.scrapper.dto.request.LinkUpdate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BotService {

    private final BotWebClient botWebClient;

    public String postUpdate(Long id, String url, String description, List<Long> tgChatIds) {
        return botWebClient.postUpdate(new LinkUpdate(id, url, description, tgChatIds));
    }
}
