package edu.java.bot.dto.response;

public record ApiErrorResponse(
    String description,
    String code
) {
}
