package edu.java.bot.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record LinkUpdate(
    @NotNull(message = "Идентификатор должен существовать")
    Long id,
    @NotBlank(message = "Ссылка не должна быть пустой")
    String url,
    List<String> description,
    List<Long> tgChatIds
) {
}
