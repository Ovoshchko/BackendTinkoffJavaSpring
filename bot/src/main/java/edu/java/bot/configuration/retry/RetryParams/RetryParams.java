package edu.java.bot.configuration.retry.RetryParams;

import edu.java.bot.configuration.retry.backoff.RetryBackoff;
import org.springframework.http.HttpStatusCode;
import java.util.Set;

public record RetryParams(RetryBackoff backoff, Set<HttpStatusCode> codes) {
}
