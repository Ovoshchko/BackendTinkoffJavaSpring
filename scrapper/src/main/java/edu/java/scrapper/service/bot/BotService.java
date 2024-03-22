package edu.java.scrapper.service.bot;

import edu.java.scrapper.clients.bot.BotWebClient;
import edu.java.scrapper.dto.request.LinkUpdate;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BotService {

    private final BotWebClient botWebClient;

    public String postUpdate(Long id, URI url, List<String> description, List<Long> tgChatIds) {
        return botWebClient.postUpdate(new LinkUpdate(id, url, description, tgChatIds));
    }
}
