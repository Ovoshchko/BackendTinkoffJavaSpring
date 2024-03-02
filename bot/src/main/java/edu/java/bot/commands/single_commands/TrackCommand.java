package edu.java.bot.commands.single_commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.link_validators.LinkValidation;
import edu.java.bot.service.ScrapperService;
import edu.java.bot.utils.LinkTypes;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TrackCommand implements Executable {

    private final LinkValidation linkValidation;
    private final ScrapperService scrapperService;

    @Override
    public String name() {
        return "/track";
    }

    @Override
    public String description() {
        return "Делает ссылку отслеживаемой. Применение: /track link1 link2 ....";
    }

    @Override
    public String execute(Update update, List<String> args) {
        StringBuilder answer = new StringBuilder();
        LinkTypes type;
        for (String link : args) {
            type = linkValidation.isValid(link);
            answer.append("_");
            if (type == LinkTypes.VALID) {
                answer.append(scrapperService.addLink(update.message().chat().id(), link));
            }
            answer.append("_ - ").append(type.getValue());
            answer.append("\n");
        }
        return answer.toString();
    }
}
