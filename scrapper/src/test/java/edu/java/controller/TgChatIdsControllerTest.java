package edu.java.controller;

import edu.java.service.TgChatIdsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TgChatIdsControllerTest {

    @Mock
    TgChatIdsService tgChatIdsService;

    @InjectMocks
    TgChatIdsController tgChatIdsController;

    @Test
    void registerUserChat_ReturnsHttpStatusOk_WhenRegistrationSuccessful() {
        long id = 12345L;

        when(tgChatIdsService.registerUserChat(id)).thenReturn(ResponseEntity.ok().build());

        ResponseEntity responseEntity = tgChatIdsController.registerUserChat(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteUserChat_ReturnsHttpStatusOk_WhenDeletionSuccessful() {
        long id = 12345L;

        when(tgChatIdsService.deleteUserChat(id)).thenReturn(ResponseEntity.ok().build());

        ResponseEntity responseEntity = tgChatIdsController.deleteUserChat(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}

