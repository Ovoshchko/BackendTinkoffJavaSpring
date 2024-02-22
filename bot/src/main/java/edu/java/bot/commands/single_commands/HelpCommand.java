package edu.java.bot.commands.single_commands;

import com.pengrad.telegrambot.model.Update;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Executable {

    private final List<Executable> commandList;

    @Autowired
    public HelpCommand(List<Executable> commandList) {
        this.commandList = commandList;
    }

    @Override
    public String name() {
        return "/help";
    }

    @Override
    public String description() {
        return "Возвращает доступные команды с их описанием";
    }

    @Override
    public String execute(Update update, List<String> args) {
        return commandList.stream()
            .map(
                command -> "`" + command.name() + " ` - _" + command.description() + "_\n"
            ).collect(Collectors.joining());
    }

}
