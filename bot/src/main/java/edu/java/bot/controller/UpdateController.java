package edu.java.bot.controller;

import edu.java.bot.dto.request.LinkUpdate;
import edu.java.bot.service.UpdateService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/updates")
@AllArgsConstructor
public class UpdateController {

    private final UpdateService updateService;

    @PostMapping
    @Operation(summary = "Обновление ссылки")
    public ResponseEntity postUpdate(@RequestBody @Valid LinkUpdate linkUpdate) {
        return updateService.postUpdate(linkUpdate);
    }
}
