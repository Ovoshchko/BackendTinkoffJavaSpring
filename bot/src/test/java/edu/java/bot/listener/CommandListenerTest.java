package edu.java.bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.CommandHandler;
import edu.java.bot.commands.single_commands.HelpCommand;
import edu.java.bot.commands.single_commands.ListCommand;
import edu.java.bot.commands.single_commands.StartCommand;
import edu.java.bot.commands.single_commands.TrackCommand;
import edu.java.bot.commands.single_commands.UntrackCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommandListenerTest {

    private final static TelegramBot BOT_MOCK = Mockito.mock(TelegramBot.class);
    private final static CommandHandler COMMAND_HANDLER_MOCK = mock(CommandHandler.class);
    private final static Chat CHAT = mock(Chat.class);
    private final static Message MESSAGE = mock(Message.class);
    private final static Update UPDATE = mock(Update.class);
    private final static CommandListener commandListener = new CommandListener(BOT_MOCK, COMMAND_HANDLER_MOCK);

    @BeforeAll
    static void init() {
        when(CHAT.id()).thenReturn(1L);
        when(CHAT.firstName()).thenReturn("Ovoshch");
        when(MESSAGE.chat()).thenReturn(CHAT);
        when(MESSAGE.text()).thenReturn("text");
        when(UPDATE.message()).thenReturn(MESSAGE);
    }

    @Test
    void process() {

        List<Update> updates = Arrays.asList(
            UPDATE,
            UPDATE,
            new Update()
        );

        when(BOT_MOCK.execute(Mockito.any(SendMessage.class))).thenReturn(null);

        commandListener.process(updates);

        verify(COMMAND_HANDLER_MOCK, times(updates.size() - 1)).handle(Mockito.any(Update.class));
        verify(BOT_MOCK, times(updates.size() - 1)).execute(Mockito.any());
    }
}
