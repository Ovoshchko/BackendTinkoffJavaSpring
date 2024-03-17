package edu.java.scrapper.dto.response;

public record ApiErrorResponse(
    String description,
    String code
) {
}
