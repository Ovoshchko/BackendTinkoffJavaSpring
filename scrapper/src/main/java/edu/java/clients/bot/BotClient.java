package edu.java.clients.bot;

import edu.java.dto.request.LinkUpdate;

public interface BotClient {

    String postUpdate(LinkUpdate linkUpdate);
}
