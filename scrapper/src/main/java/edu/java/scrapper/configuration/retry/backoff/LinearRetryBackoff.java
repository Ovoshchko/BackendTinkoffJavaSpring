package edu.java.scrapper.configuration.retry.backoff;

import java.time.Duration;

public record LinearRetryBackoff(Duration initialDelay, Duration maxDelay, int multiplier) implements RetryBackoff {
    @Override
    public Duration getDelay(int attempts) {
        Duration delay = initialDelay.multipliedBy((long) multiplier * attempts);
        return delay.compareTo(maxDelay) < 0 ? delay : maxDelay;
    }
}
