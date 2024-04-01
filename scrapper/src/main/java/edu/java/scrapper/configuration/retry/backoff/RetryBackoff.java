package edu.java.scrapper.configuration.retry.backoff;

import java.time.Duration;

public interface RetryBackoff {

    Duration getDelay(int attempts);
}
