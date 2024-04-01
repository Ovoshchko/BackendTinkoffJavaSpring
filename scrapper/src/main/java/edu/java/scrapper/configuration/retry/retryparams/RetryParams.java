package edu.java.scrapper.configuration.retry.retryparams;

import edu.java.scrapper.configuration.retry.backoff.RetryBackoff;
import org.springframework.http.HttpStatusCode;
import java.util.Set;

public record RetryParams(RetryBackoff backoff, Set<HttpStatusCode> codes) {
}
