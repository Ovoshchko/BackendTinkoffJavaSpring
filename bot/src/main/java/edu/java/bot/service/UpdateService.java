package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dto.request.LinkUpdate;
import edu.java.bot.listener.CommandListener;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateService {

    private final CommandListener commandListener;

    public void postUpdate(LinkUpdate linkUpdate) {

        String description = String.join("", linkUpdate.description());

        for (Long id : linkUpdate.tgChatIds()) {
            commandListener.sendMessage(new SendMessage(
                id,
                description
            ));
        }
    }
}
