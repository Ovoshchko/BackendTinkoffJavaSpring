package edu.java.scrapper.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ListAnswer(
    @JsonProperty(value = "items")
    List<Answer> answers
) {
}
