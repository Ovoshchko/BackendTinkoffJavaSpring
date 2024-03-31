package edu.java.bot.configuration.retry;

import edu.java.bot.configuration.retry.RetryParams.RetryParams;
import edu.java.bot.configuration.retry.backoff.ConstantRetryBackoff;
import edu.java.bot.configuration.retry.backoff.ExponentialRetryBackoff;
import edu.java.bot.configuration.retry.backoff.LinearRetryBackoff;
import edu.java.bot.configuration.retry.backoff.RetryBackoff;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class RetryClientConfig {

    private final RetryConfig retryConfig;

    @Bean
    public ExchangeFilterFunction retryExchangeFilter(RetryParams retryParams) {
        return new RetryExchangeFilterFunction(retryConfig.getMaxAttempts(), retryParams);
    }

    @Bean
    public RetryParams retryParams(RetryBackoff retryBackoff) {
        return new RetryParams(
            retryBackoff,
            retryConfig.getCodes().stream().map(HttpStatusCode::valueOf).collect(Collectors.toSet())
        );
    }

    @Bean
    @ConditionalOnProperty(prefix = "client", name = "backoff", havingValue = "exponent")
    public RetryBackoff exponentRetryBackoff() {
        return new ExponentialRetryBackoff(
            retryConfig.getInitialInterval(),
            retryConfig.getMaxInterval(),
            retryConfig.getMultiplier()
        );
    }

    @Bean
    @ConditionalOnProperty(prefix = "client", name = "backoff", havingValue = "linear")
    public RetryBackoff linearRetryBackoff() {
        return new LinearRetryBackoff(
            retryConfig.getInitialInterval(),
            retryConfig.getMaxInterval(),
            retryConfig.getMultiplier()
        );
    }

    @Bean
    @ConditionalOnProperty(prefix = "client", name = "backoff", havingValue = "constant")
    public RetryBackoff constantRetryBackoff() {
        return new ConstantRetryBackoff(retryConfig.getInitialInterval());
    }

    private class RetryExchangeFilterFunction implements ExchangeFilterFunction {

        private final int maxAttempts;
        private final RetryParams retryParams;

        public RetryExchangeFilterFunction(int maxAttempts, RetryParams retryParams) {
            this.maxAttempts = maxAttempts;
            this.retryParams = retryParams;
        }

        @Override
        public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
            return retryAttempt(request, next, 1);
        }

        private Mono<ClientResponse> retryAttempt(ClientRequest request, ExchangeFunction next, int attempt) {
            return next.exchange(request)
                .flatMap(clientResponse -> {
                    if ((retryParams.codes().contains(clientResponse.statusCode())) && (maxAttempts > attempt)) {
                        return Mono.delay(retryParams.backoff().getDelay(attempt))
                            .then(Mono.defer(() -> retryAttempt(request, next, attempt + 1)));
                    }
                    return Mono.just(clientResponse);
                });
        }
    }
}
