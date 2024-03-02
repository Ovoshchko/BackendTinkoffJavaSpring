package edu.java.bot.service;

import edu.java.bot.dto.request.LinkUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateServiceTest {

    private UpdateService updateService;

    @BeforeEach
    void setUp() {
        updateService = new UpdateService();
    }

    @Test
    void postUpdateReturns200() {
        LinkUpdate validLinkUpdate = new LinkUpdate(1L, "https://ok.com", "ok", List.of(1L));
        ResponseEntity responseEntity = updateService.postUpdate(validLinkUpdate);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Обновление обработано", responseEntity.getBody());
    }
}

