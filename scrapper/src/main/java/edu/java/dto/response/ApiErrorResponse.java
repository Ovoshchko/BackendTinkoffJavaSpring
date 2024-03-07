package edu.java.dto.response;

public record ApiErrorResponse(
    String description,
    String code
) {
}
