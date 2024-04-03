package edu.java.bot.configuration.retry.retryparams;

import edu.java.bot.configuration.retry.backoff.RetryBackoff;
import java.util.Set;
import org.springframework.http.HttpStatusCode;

public record RetryParams(RetryBackoff backoff, Set<HttpStatusCode> codes) {
}
