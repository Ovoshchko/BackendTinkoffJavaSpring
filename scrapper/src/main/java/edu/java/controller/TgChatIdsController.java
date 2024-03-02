package edu.java.controller;

import edu.java.service.TgChatIdsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tg-chat/{id}")
@AllArgsConstructor
public class TgChatIdsController {

    private final TgChatIdsService tgChatIdsService;

    @PostMapping
    @Operation(summary = "Добавление чата в список")
    public ResponseEntity registerUserChat(@PathVariable Long id) {
        return tgChatIdsService.registerUserChat(id);
    }

    @DeleteMapping
    @Operation(summary = "Удаление чата из списка")
    public ResponseEntity deleteUserChat(@PathVariable Long id) {
        return tgChatIdsService.deleteUserChat(id);
    }
}
