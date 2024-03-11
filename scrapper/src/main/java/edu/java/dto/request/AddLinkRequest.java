package edu.java.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddLinkRequest(
    @NotBlank(message = "Ссылка не должна быть пустой")
    String link
) {
}
