package edu.java.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StackoverflowResponse(List<QuestionResponse> items) {
    public record QuestionResponse(
        @JsonProperty("question_id")
        Long id,
        String link,
        @JsonProperty("creation_date")
        OffsetDateTime createdAt,
        @JsonProperty("last_activity_date")
        OffsetDateTime lastActivityDate
    ) {
    }
}
