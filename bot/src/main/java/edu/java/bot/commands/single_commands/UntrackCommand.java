package edu.java.bot.commands.single_commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.data_base_imitation.LinksDB;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UntrackCommand implements Executable {

    private final LinksDB linksDB;

    @Override
    public String name() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Отменяет отслеживание переданных ссылок, если таковые были. Применение: /untrack link1 link2 ....";
    }

    @Override
    public String execute(Update update, List<String> args) {
        StringBuilder answer = new StringBuilder();
        for (String link : args) {
            linksDB.deleteLink(update.message().chat().id(), link);
            answer.append("_")
                .append(link)
                .append("_ канула в небытие. Теперь я не буду вас оповещать об изменениях по данной ссылке.\n");
        }
        return answer.toString();
    }
}
