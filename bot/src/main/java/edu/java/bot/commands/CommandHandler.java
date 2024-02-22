package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.single_commands.Executable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandHandler {

    private final static String NOT_FOUND =
        "Простите, такой команды найдено не было. Пожалуйста, воспользуйтесь командой /help для просмотра доступных.";

    private final Map<String, Executable> commandList;

    @Autowired
    public CommandHandler(List<Executable> commandList) {
        this.commandList = new HashMap<>();
        setCommandList(commandList);
    }

    public SendMessage handle(Update update) {
        String[] message = update.message().text().split(" ");
        String answer = NOT_FOUND;
        if (commandList.containsKey(message[0])) {
            answer =
                commandList.get(message[0]).execute(update, List.of(Arrays.copyOfRange(message, 1, message.length)));
        }
        return new SendMessage(update.message().chat().id(), answer).parseMode(ParseMode.Markdown);
    }

    private void setCommandList(List<Executable> commandList) {
        for (Executable command: commandList) {
            this.commandList.put(command.name(), command);
        }
    }
}
