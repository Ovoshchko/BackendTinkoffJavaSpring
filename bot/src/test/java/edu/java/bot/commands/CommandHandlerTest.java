package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.single_commands.HelpCommand;
import edu.java.bot.commands.single_commands.ListCommand;
import edu.java.bot.commands.single_commands.StartCommand;
import edu.java.bot.commands.single_commands.TrackCommand;
import edu.java.bot.commands.single_commands.UntrackCommand;
import edu.java.bot.data_base_imitation.LinksDB;
import edu.java.bot.data_base_imitation.UserDB;
import edu.java.bot.link_validators.LinkValidation;
import edu.java.bot.service.ScrapperService;
import edu.java.bot.utils.LinkTypes;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommandHandlerTest {

    private final static Chat CHAT = mock(Chat.class);
    private final static Message MESSAGE = mock(Message.class);
    private final static Update UPDATE = mock(Update.class);
    private final static LinkValidation LINK_VALIDATION = mock(LinkValidation.class);
    private final static ScrapperService SCRAPPER_SERVICE = mock(ScrapperService.class);
    private final static CommandHandler COMMAND_HANDLER = new CommandHandler(
        List.of(
            new HelpCommand(new ArrayList<>()),
            new ListCommand(SCRAPPER_SERVICE),
            new StartCommand(SCRAPPER_SERVICE),
            new TrackCommand(LINK_VALIDATION, SCRAPPER_SERVICE),
            new UntrackCommand(SCRAPPER_SERVICE)
        )
    );

    @BeforeAll
    static void init() {
        when(CHAT.id()).thenReturn(1L);
        when(CHAT.firstName()).thenReturn("Ovoshch");
        when(MESSAGE.chat()).thenReturn(CHAT);
        when(UPDATE.message()).thenReturn(MESSAGE);
        when(SCRAPPER_SERVICE.addLink(anyLong(), anyString())).thenReturn("https://github.com");
        when(SCRAPPER_SERVICE.deleteLink(anyLong(), anyString())).thenReturn("https://stackoverflow.com");
        when(SCRAPPER_SERVICE.getAllLinks(anyLong())).thenReturn(new ArrayList<>());
        when(SCRAPPER_SERVICE.registerUserChat(anyLong())).thenReturn("Чат зарегистрирован");
        when(LINK_VALIDATION.isValid(anyString())).thenReturn(LinkTypes.VALID);
    }

    @Test
    void handleExisting() {
        when(MESSAGE.text()).thenReturn("/track https://github.com");

        SendMessage result = COMMAND_HANDLER.handle(UPDATE);
        SendMessage answer = new SendMessage(1L, "_https://github.com_ - ссылка успешно зарегистрирована.\n")
            .parseMode(ParseMode.Markdown);
        assertThat(result.getParameters()).isEqualTo(answer.getParameters());
    }

    @Test
    void handleNonExisting() {
        when(MESSAGE.text()).thenReturn("/aboba");

        SendMessage result = COMMAND_HANDLER.handle(UPDATE);
        SendMessage answer = new SendMessage(
            1L,
            "Простите, такой команды найдено не было. Пожалуйста, воспользуйтесь командой /help для просмотра доступных."
        )
            .parseMode(ParseMode.Markdown);
        assertThat(result.getParameters()).isEqualTo(answer.getParameters());
    }
}
