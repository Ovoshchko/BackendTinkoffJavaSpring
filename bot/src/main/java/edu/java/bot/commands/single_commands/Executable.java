package edu.java.bot.commands.single_commands;

import com.pengrad.telegrambot.model.Update;
import java.util.List;

public interface Executable {

    String name();

    String description();

    String execute(Update update, List<String> args);
}
