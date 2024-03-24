package edu.java.scrapper.service.chat;

import edu.java.scrapper.exception.AlreadyExistsException;
import edu.java.scrapper.repository.jdbc.JdbcUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JdbcTgChatIdsServiceTest {

    public static final long TG_CHAT_ID = 12345L;
    @Mock
    private JdbcUserRepository jdbcUserRepository;
    @InjectMocks
    private JdbcTgChatIdsService jdbcTgChatIdsService;

    @Test
    void registerUserChat_ReturnsOkResponse() {

        when(jdbcUserRepository.add(TG_CHAT_ID)).thenReturn(1);

        jdbcTgChatIdsService.registerUserChat(TG_CHAT_ID);
    }

    @Test
    void deleteUserChat_ReturnsOkResponse() {

        when(jdbcUserRepository.remove(TG_CHAT_ID)).thenReturn(1);

        jdbcTgChatIdsService.deleteUserChat(TG_CHAT_ID);
    }

    @Test
    void registerUserChat_ReturnsApiErrorResponse() {

        when(jdbcUserRepository.add(TG_CHAT_ID)).thenThrow(AlreadyExistsException.class);

        assertThrows(AlreadyExistsException.class, () -> jdbcTgChatIdsService.registerUserChat(TG_CHAT_ID));
    }

}

