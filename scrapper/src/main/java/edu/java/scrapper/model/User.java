package edu.java.scrapper.model;

import java.time.LocalDate;

public record User(
    Long tgId,
    LocalDate createdAt
) {
}
