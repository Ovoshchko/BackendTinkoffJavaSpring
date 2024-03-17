package edu.java.scrapper.service;

import edu.java.scrapper.exception.AlreadyExistsException;
import edu.java.scrapper.repository.User.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TgChatIdsServiceTest {

    @Mock
    private UserRepository jdbcUserRepository;
    @InjectMocks
    private TgChatIdsService tgChatIdsService;

    @Test
    void registerUserChat_ReturnsOkResponse() {
        long id = 12345L;

        when(jdbcUserRepository.add(id)).thenReturn(1);

        tgChatIdsService.registerUserChat(id);
    }

    @Test
    void deleteUserChat_ReturnsOkResponse() {
        long id = 12345L;

        when(jdbcUserRepository.remove(id)).thenReturn(1);

        tgChatIdsService.deleteUserChat(id);
    }

    @Test
    void registerUserChat_ReturnsApiErrorResponse() {
        long id = 666L;

        when(jdbcUserRepository.add(id)).thenThrow(AlreadyExistsException.class);

        assertThrows(AlreadyExistsException.class, () -> tgChatIdsService.registerUserChat(id));
    }


}

