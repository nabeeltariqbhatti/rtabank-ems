package ae.rakbank.gateway;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RateLimiterConfiguration {

    @Bean
    public GlobalFilter customRateLimiter() {
        return (exchange, chain) -> {
            String clientIp = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");
            if (clientIp == null) {
                clientIp = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress();
            }

            Bucket bucket = resolveBucket(clientIp);

            if (bucket.tryConsume(1)) {
                return chain.filter(exchange);
            } else {
                exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                return exchange.getResponse().setComplete();
            }
        };
    }

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private Bucket resolveBucket(String clientIp) {
        return cache.computeIfAbsent(clientIp, this::newBucket);
    }

    private Bucket newBucket(String clientIp) {
        Bandwidth bandwidth = Bandwidth.simple(50, Duration.ofMinutes(1));
        return Bucket.builder()
                .addLimit(bandwidth)
                .build();

    }
}
