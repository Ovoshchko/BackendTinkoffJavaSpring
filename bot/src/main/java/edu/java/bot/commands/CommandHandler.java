package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.single_commands.Executable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service public class CommandHandler {

    private final static String NOT_FOUND =
        "Простите, такой команды найдено не было. Пожалуйста, воспользуйтесь командой /help для просмотра доступных.";

    private final List<Executable> commandList;

    @Autowired public CommandHandler(List<Executable> commandList) {
        this.commandList = commandList;
    }

    public SendMessage handle(Update update) {
        String[] message = update.message().text().split(" ");
        Optional<Executable> command = commandList.stream().filter(com -> com.name().equals(message[0])).findFirst();
        String response = "";
        if (command.isPresent()) {
            response =
                command.get().execute(update, Arrays.stream(Arrays.copyOfRange(message, 1, message.length)).toList());
        } else {
            response = NOT_FOUND;
        }
        return new SendMessage(update.message().chat().id(), response).parseMode(ParseMode.Markdown);
    }
}
