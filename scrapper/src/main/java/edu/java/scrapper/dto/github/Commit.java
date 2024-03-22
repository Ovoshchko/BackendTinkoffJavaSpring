package edu.java.scrapper.dto.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.net.URI;
import java.time.OffsetDateTime;

public record Commit(
    CommitData commit
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CommitData(
        Author author,
        URI url,
        int commentCount
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Author(
            String name,
            OffsetDateTime date
        ) {}

    }
}
