package edu.java.scrapper.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Link {
    private Long id;
    private String link;
    private LocalDateTime lastCheck;
}
