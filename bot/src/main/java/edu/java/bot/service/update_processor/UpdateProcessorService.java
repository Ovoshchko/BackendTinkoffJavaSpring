package edu.java.bot.service.update_processor;

import edu.java.bot.dto.request.LinkUpdate;

public interface UpdateProcessorService {

    void postUpdate(LinkUpdate linkUpdate);
}
