package edu.java.scrapper.dto.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubResponse(
    String name,
    Owner owner,
    @JsonProperty("created_at")
    OffsetDateTime createdAt,
    @JsonProperty("updated_at")
    OffsetDateTime lastUpdateTime
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Owner(
        String login
    ) {
    }
}
