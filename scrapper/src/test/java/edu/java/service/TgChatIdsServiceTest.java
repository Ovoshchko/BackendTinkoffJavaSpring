package edu.java.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TgChatIdsServiceTest {

    private TgChatIdsService tgChatIdsService;

    @BeforeEach
    void setUp() {
        tgChatIdsService = new TgChatIdsService();
    }

    @Test
    void registerUserChat_ReturnsOkResponse_WhenCalled() {
        long id = 12345L;

        ResponseEntity responseEntity = tgChatIdsService.registerUserChat(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Чат зарегистрирован", responseEntity.getBody());
    }

    @Test
    void deleteUserChat_ReturnsOkResponse_WhenCalled() {
        long id = 12345L;

        ResponseEntity responseEntity = tgChatIdsService.deleteUserChat(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Чат успешно удалён", responseEntity.getBody());
    }
}

