package edu.java.bot.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

public record LinkUpdate(
    @NotNull(message = "Идентификатор должен существовать")
    Long id,
    URI url,
    List<String> description,
    List<Long> tgChatIds
) {
}
