package edu.java.scrapper.configuration.retry.backoff;

import java.time.Duration;

public record ConstantRetryBackoff(Duration initialDelay) implements RetryBackoff {
    @Override
    public Duration getDelay(int attempts) {
        return initialDelay;
    }
}

