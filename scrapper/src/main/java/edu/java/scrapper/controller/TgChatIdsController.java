package edu.java.scrapper.controller;

import edu.java.scrapper.service.chat.TgChatIdsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tg-chat/{id}")
@RequiredArgsConstructor
public class TgChatIdsController {

    private final TgChatIdsService jooqTgChatIdsService;

    @PostMapping
    @Operation(summary = "Добавление чата в список")
    public ResponseEntity registerUserChat(@PathVariable Long id) {
        jooqTgChatIdsService.registerUserChat(id);
        return ResponseEntity.status(HttpStatus.OK).body("Пользователь успешно добавлен.");
    }

    @DeleteMapping
    @Operation(summary = "Удаление чата из списка")
    public ResponseEntity deleteUserChat(@PathVariable Long id) {
        jooqTgChatIdsService.deleteUserChat(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
