package edu.java.controller;

import edu.java.service.TgChatIdsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TgChatIdsControllerTest {

    @Mock
    TgChatIdsService tgChatIdsService;

    @InjectMocks
    TgChatIdsController tgChatIdsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

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

