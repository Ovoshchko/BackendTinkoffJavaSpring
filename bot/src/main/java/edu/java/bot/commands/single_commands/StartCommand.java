package edu.java.bot.commands.single_commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.ScrapperService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class StartCommand implements Executable {

    private final ScrapperService scrapperService;

    @Override
    public String name() {
        return "/start";
    }

    @Override
    public String description() {
        return "Регистрирует пользователя и запускает чат с ботом.";
    }

    @Override
    public String execute(Update update, List<String> args) {
        return scrapperService.registerUserChat(update.message().chat().id());
    }
}
