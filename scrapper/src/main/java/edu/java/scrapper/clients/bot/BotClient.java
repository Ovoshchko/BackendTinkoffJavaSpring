package edu.java.scrapper.clients.bot;

import edu.java.scrapper.dto.request.LinkUpdate;

public interface BotClient {

    String postUpdate(LinkUpdate linkUpdate);
}
