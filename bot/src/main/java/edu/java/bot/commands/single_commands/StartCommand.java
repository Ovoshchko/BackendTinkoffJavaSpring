package edu.java.bot.commands.single_commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.data_base_imitation.UserDB;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Executable {

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
        UserDB.addUser(update.message().chat().id(), update.message().chat().firstName());
        return "Hello, _" + update.message().chat().firstName()
            + "_. Ты теперь зарегистрирован.Ввведи /help, что бы посмотреть доступные команды, "
            + "либо воспользуйся функцией меню.";
    }
}
