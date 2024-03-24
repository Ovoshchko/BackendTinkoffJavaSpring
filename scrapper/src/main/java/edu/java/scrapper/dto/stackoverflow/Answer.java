package edu.java.scrapper.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Answer(
    Owner owner,
    @JsonProperty(value = "answer_id")
    long answerId,
    @JsonProperty(value = "question_id")
    long questionId
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Owner(
        @JsonProperty(value = "display_name")
        String displayName
    ) {}
}
