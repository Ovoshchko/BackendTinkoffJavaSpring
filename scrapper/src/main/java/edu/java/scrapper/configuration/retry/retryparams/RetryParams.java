package edu.java.scrapper.configuration.retry.retryparams;

import edu.java.scrapper.configuration.retry.backoff.RetryBackoff;
import java.util.Set;
import org.springframework.http.HttpStatusCode;

public record RetryParams(RetryBackoff backoff, Set<HttpStatusCode> codes) {
}
