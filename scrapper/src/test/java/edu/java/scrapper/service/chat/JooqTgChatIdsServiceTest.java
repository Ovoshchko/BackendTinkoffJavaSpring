package edu.java.scrapper.service.chat;

import edu.java.scrapper.exception.AlreadyExistsException;
import edu.java.scrapper.repository.jooq.JooqUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JooqTgChatIdsServiceTest {

    public static final long TG_CHAT_ID = 12345L;
    @Mock
    private JooqUserRepository jooqUserRepository;
    @InjectMocks
    private JooqTgChatIdsService jooqTgChatIdsService;

    @Test
    void registerUserChat_ReturnsOkResponse() {

        when(jooqUserRepository.add(TG_CHAT_ID)).thenReturn(1);

        jooqTgChatIdsService.registerUserChat(TG_CHAT_ID);
    }

    @Test
    void deleteUserChat_ReturnsOkResponse() {

        when(jooqUserRepository.remove(TG_CHAT_ID)).thenReturn(1);

        jooqTgChatIdsService.deleteUserChat(TG_CHAT_ID);
    }

    @Test
    void registerUserChat_ReturnsApiErrorResponse() {

        when(jooqUserRepository.add(TG_CHAT_ID)).thenThrow(AlreadyExistsException.class);

        assertThrows(AlreadyExistsException.class, () -> jooqTgChatIdsService.registerUserChat(TG_CHAT_ID));
    }
}
