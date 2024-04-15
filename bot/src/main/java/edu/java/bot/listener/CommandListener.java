package edu.java.bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.CommandHandler;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandListener implements UpdatesListener {

    public static final String MESSAGES_PROCESSED = "messages.processed";
    private final TelegramBot bot;
    private final CommandHandler commandHandler;
    Counter processedMessages = Metrics.counter(MESSAGES_PROCESSED);

    @Autowired
    public CommandListener(TelegramBot bot, CommandHandler commandHandler, MeterRegistry meterRegistry) {
        this.bot = bot;
        this.commandHandler = commandHandler;
        this.bot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> list) {
        for (Update update : list) {
            if (update.message() != null) {
                processedMessages.increment();
                sendMessage(commandHandler.handle(update));
            }
        }
        return CONFIRMED_UPDATES_ALL;
    }

    public void sendMessage(SendMessage message) {
        bot.execute(message);
    }
}
