package edu.java.bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.CommandHandler;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component public class CommandListener implements UpdatesListener {

    private final TelegramBot bot;
    private final CommandHandler commandHandler;

    @Autowired public CommandListener(TelegramBot bot, CommandHandler commandHandler) {
        this.bot = bot;
        this.commandHandler = commandHandler;
        this.bot.setUpdatesListener(this);
    }

    @Override public int process(List<Update> list) {
        for (Update update : list) {
            if (update.message() != null) {
                bot.execute(commandHandler.handle(update));
            }
        }
        return CONFIRMED_UPDATES_ALL;
    }
}
