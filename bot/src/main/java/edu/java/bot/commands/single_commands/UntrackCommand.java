package edu.java.bot.commands.single_commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.ScrapperService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UntrackCommand implements Executable {

    private final ScrapperService scrapperService;

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
            answer.append("_")
                .append(scrapperService.deleteLink(update.message().chat().id(), link))
                .append("_ - успешно удалена")
                .append("\n");
        }
        return answer.toString();
    }
}
