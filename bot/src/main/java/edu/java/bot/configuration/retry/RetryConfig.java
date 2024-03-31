package edu.java.bot.configuration.retry;

import java.time.Duration;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "client.retry", ignoreUnknownFields = false)
@Data
@NoArgsConstructor
public class RetryConfig {
    private Duration initialInterval;
    private int maxAttempts;
    private Duration maxInterval;
    private int multiplier;
    private Set<Integer> codes;
}
