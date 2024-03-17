package edu.java.scrapper.controller;

import edu.java.scrapper.service.chat.JdbcTgChatIdsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class TgChatIdsControllerTest {

    @Mock
    JdbcTgChatIdsService jdbcTgChatIdsService;

    @InjectMocks
    TgChatIdsController tgChatIdsController;

    @Test
    void registerUserChat_ReturnsHttpStatusOk_WhenRegistrationSuccessful() {
        long id = 12345L;

        doNothing().when(jdbcTgChatIdsService).registerUserChat(anyLong());

        ResponseEntity responseEntity = tgChatIdsController.registerUserChat(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteUserChat_ReturnsHttpStatusOk_WhenDeletionSuccessful() {
        long id = 12345L;

        doNothing().when(jdbcTgChatIdsService).deleteUserChat(anyLong());

        ResponseEntity responseEntity = tgChatIdsController.deleteUserChat(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}

