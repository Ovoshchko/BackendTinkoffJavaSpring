package edu.java.bot.commands.single_commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.data_base_imitation.LinksDB;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Executable {

    private final static String EMPTY_LIST = "*Ссылок*. net";

    @Override
    public String name() {
        return "/list";
    }

    @Override
    public String description() {
        return "Возвращает все отслеживаемые ссылки пользователя";
    }

    @Override
    public String execute(Update update, List<String> args) {
        StringBuilder answer = new StringBuilder();
        List<String> links = LinksDB.getUsersLinks(update.message().chat().id());
        if ((links==null)||(links.isEmpty())) {
            return EMPTY_LIST;
        }
        links.forEach(link -> answer.append("*Ссылка* - ").append(link).append("\n"));
        return answer.toString();
    }
}
