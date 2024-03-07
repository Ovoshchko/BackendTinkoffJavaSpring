package edu.java.service;

import edu.java.exception.AlreadyExistsException;
import edu.java.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TgChatIdsServiceTest {

    private TgChatIdsService tgChatIdsService;

    @BeforeEach
    void setUp() {
        tgChatIdsService = new TgChatIdsService();
    }

    @Test
    void registerUserChat_ReturnsOkResponse() {
        long id = 12345L;

        ResponseEntity responseEntity = tgChatIdsService.registerUserChat(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Чат зарегистрирован", responseEntity.getBody());
    }

    @Test
    void deleteUserChat_ReturnsOkResponse() {
        long id = 12345L;

        ResponseEntity responseEntity = tgChatIdsService.deleteUserChat(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Чат успешно удалён", responseEntity.getBody());
    }

    @Test
    void registerUserChat_ReturnsApiErrorResponse() {
        long id = 666L;

        assertThrows(AlreadyExistsException.class, () -> tgChatIdsService.registerUserChat(id));
    }

    @Test
    void deleteUserChat_ReturnsApiErrorResponse_404() {
        long id = 666L;

        assertThrows(NotFoundException.class, () -> tgChatIdsService.deleteUserChat(id));
    }

}

