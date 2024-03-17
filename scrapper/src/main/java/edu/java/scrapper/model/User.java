package edu.java.scrapper.model;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    Long tgId;
    LocalDate createdAt;
}
