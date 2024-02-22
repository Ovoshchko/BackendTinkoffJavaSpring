package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.commands.single_commands.Executable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkBot extends TelegramBot {

    @Autowired
    public LinkBot(ApplicationConfig applicationConfig, List<Executable> commands) {
        super(applicationConfig.telegramToken());
        execute(createCommandsMenu(commands));
    }

    private SetMyCommands createCommandsMenu(List<Executable> commands) {
        return new SetMyCommands(
            commands.stream().map(command -> new BotCommand(command.name(), command.description()))
                .toArray(BotCommand[]::new)
        );
    }
}
