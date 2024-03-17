package edu.java.scrapper.controller;

import edu.java.scrapper.dto.request.AddLinkRequest;
import edu.java.scrapper.dto.request.RemoveLinkRequest;
import edu.java.scrapper.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/links")
@AllArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @SneakyThrows
    @GetMapping
    @Operation(summary = "Получение всех отслеживаемых ссылок")
    public ResponseEntity getAllLinks(@RequestParam("Tg-Chat-Id") Long tgChatId) {
        return linkService.getAllLinks(tgChatId);
    }

    @PostMapping
    @Operation(summary = "Добавление ссылок в список отслеживаемых")
    public ResponseEntity addLink(
        @RequestParam("Tg-Chat-Id") Long tgChatId,
        @RequestBody @Valid AddLinkRequest addLinkRequest
    ) {
        return linkService.addLink(tgChatId, addLinkRequest);
    }

    @SneakyThrows
    @DeleteMapping
    @Operation(summary = "Удаление ссылки из списка отслеживаемых")
    public ResponseEntity deleteLink(
        @RequestParam("Tg-Chat-Id") Long tgChatId,
        @RequestBody @Valid RemoveLinkRequest removeLinkRequest
    ) {
        return linkService.deleteLink(tgChatId, removeLinkRequest);
    }
}
