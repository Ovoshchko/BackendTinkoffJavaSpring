package edu.java.bot.configuration.retry.backoff;

import java.time.Duration;

public interface RetryBackoff {

    Duration getDelay(int attempts);
}
