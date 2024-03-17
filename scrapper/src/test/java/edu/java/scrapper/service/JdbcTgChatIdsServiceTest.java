package edu.java.scrapper.service;

import edu.java.scrapper.exception.AlreadyExistsException;
import edu.java.scrapper.repository.jdbc.JdbcUserRepository;
import edu.java.scrapper.service.chat.JdbcTgChatIdsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JdbcTgChatIdsServiceTest {

    @Mock
    private JdbcUserRepository jdbcUserRepository;
    @InjectMocks
    private JdbcTgChatIdsService jdbcTgChatIdsService;

    @Test
    void registerUserChat_ReturnsOkResponse() {
        long id = 12345L;

        when(jdbcUserRepository.add(id)).thenReturn(1);

        jdbcTgChatIdsService.registerUserChat(id);
    }

    @Test
    void deleteUserChat_ReturnsOkResponse() {
        long id = 12345L;

        when(jdbcUserRepository.remove(id)).thenReturn(1);

        jdbcTgChatIdsService.deleteUserChat(id);
    }

    @Test
    void registerUserChat_ReturnsApiErrorResponse() {
        long id = 666L;

        when(jdbcUserRepository.add(id)).thenThrow(AlreadyExistsException.class);

        assertThrows(AlreadyExistsException.class, () -> jdbcTgChatIdsService.registerUserChat(id));
    }

}

