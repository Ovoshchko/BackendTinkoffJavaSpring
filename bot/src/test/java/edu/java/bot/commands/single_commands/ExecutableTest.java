package edu.java.bot.commands.single_commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.link_validators.LinkValidation;
import edu.java.bot.service.ScrapperService;
import edu.java.bot.utils.LinkTypes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExecutableTest {

    private final static Chat CHAT = mock(Chat.class);
    private final static Message MESSAGE = mock(Message.class);
    private final static Update UPDATE = mock(Update.class);
    private final static LinkValidation LINK_VALIDATION = mock(LinkValidation.class);
    private final static ScrapperService SCRAPPER_SERVICE = mock(ScrapperService.class);
    private final static List<Executable> COMMANDS = new ArrayList<>();
    private final static HelpCommand HELP_COMMAND = new HelpCommand(COMMANDS);
    private final static ListCommand LIST_COMMAND = new ListCommand(SCRAPPER_SERVICE);
    private final static StartCommand START_COMMAND = new StartCommand(SCRAPPER_SERVICE);
    private final static TrackCommand TRACK_COMMAND = new TrackCommand(LINK_VALIDATION, SCRAPPER_SERVICE);
    private final static UntrackCommand UNTRACK_COMMAND = new UntrackCommand(SCRAPPER_SERVICE);

    @BeforeAll
    static void init() {
        when(CHAT.id()).thenReturn(1L);
        when(CHAT.firstName()).thenReturn("Ovoshch");
        when(MESSAGE.chat()).thenReturn(CHAT);
        when(UPDATE.message()).thenReturn(MESSAGE);
        when(LINK_VALIDATION.isValid(anyString())).thenReturn(LinkTypes.VALID);
        when(SCRAPPER_SERVICE.addLink(anyLong(), anyString())).thenReturn("https://github.com");
        when(SCRAPPER_SERVICE.deleteLink(anyLong(), anyString())).thenReturn("https://stackoverflow.com");
        when(SCRAPPER_SERVICE.getAllLinks(anyLong())).thenReturn(new ArrayList<>());
        when(SCRAPPER_SERVICE.registerUserChat(anyLong())).thenReturn("Чат зарегистрирован");
        COMMANDS.addAll(List.of(LIST_COMMAND, START_COMMAND, TRACK_COMMAND, UNTRACK_COMMAND));
    }

    private static Stream<Arguments> getCommandWithName() {
        return Stream.of(
            Arguments.of(HELP_COMMAND, "/help"),
            Arguments.of(LIST_COMMAND, "/list"),
            Arguments.of(START_COMMAND, "/start"),
            Arguments.of(TRACK_COMMAND, "/track"),
            Arguments.of(UNTRACK_COMMAND, "/untrack")
        );
    }

    public static Stream<Arguments> getCommandWithResult() {
        return Stream.of(
            Arguments.of(
                HELP_COMMAND,
                HELP_COMMAND.name(),
                "`/start ` - _Регистрирует пользователя и запускает чат с ботом._\n",
                null
            ),
            Arguments.of(
                HELP_COMMAND,
                HELP_COMMAND.name(),
                "`/track ` - _Делает ссылку отслеживаемой. Применение: /track link1 link2 ...._\n",
                null
            ),
            Arguments.of(
                HELP_COMMAND,
                HELP_COMMAND.name(),
                "`/list ` - _Возвращает все отслеживаемые ссылки пользователя_\n",
                null
            ),
            Arguments.of(
                HELP_COMMAND,
                HELP_COMMAND.name(),
                "`/untrack ` - _Отменяет отслеживание переданных ссылок, если таковые были. Применение: /untrack link1 link2 ...._\n",
                null
            ),
            Arguments.of(
                START_COMMAND,
                START_COMMAND.name(),
                "Чат зарегистрирован",
                null
            ),
            Arguments.of(
                LIST_COMMAND,
                LIST_COMMAND.name(),
                "*Ссылок*. net",
                null
            ),
            Arguments.of(
                TRACK_COMMAND,
                TRACK_COMMAND.name(),
                "_https://github.com_ - ссылка успешно зарегистрирована.\n",
                List.of("https://github.com")
            ),
            Arguments.of(
                UNTRACK_COMMAND,
                UNTRACK_COMMAND.name(),
                "_https://stackoverflow.com_ - успешно удалена\n",
                List.of("https://stackoverflow.com")
            )
        );
    }

    @ParameterizedTest
    @MethodSource("getCommandWithName")
    void name(Executable command, String name) {
        assertEquals(command.name(), name);
    }

    @ParameterizedTest
    @MethodSource("getCommandWithResult")
    void execute(Executable command, String text, String answer, List<String> args) {
        when(MESSAGE.text()).thenReturn(text);

        String result = command.execute(UPDATE, args);
        assertTrue(result.contains(answer));
    }

}
