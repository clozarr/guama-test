package com.guama.purchases.app.service.impl;

import com.guama.purchases.shared.exception.RateLimitException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final StringRedisTemplate redisTemplate;
    private static final String RATE_LIMIT_KEY_PREFIX = "rate-limit:purchase-creation:user:";
    private static final Duration RATE_LIMIT_DURATION = Duration.ofMinutes(1);
    private static final String MAX_REQUESTS_PER_DURATION = "1";

    /**
     * Checks the rate limit for a specific user.
     * @param userId The unique identifier for the user.
     */
    public void checkRateLimit(String userId) {
        if (Objects.isNull(userId) || userId.isBlank()) {

            log.error("[IllegalArgumentException] User ID is null or empty when checking rate limit.");
            throw new IllegalArgumentException("User ID cannot be empty for rate limiting.");
        }

        String userSpecificKey = RATE_LIMIT_KEY_PREFIX + userId;

        log.info("GET TEST: {}", redisTemplate.opsForValue().get(userSpecificKey));

        log.info("[Ini] Checking rate limit for user {} with key {}",userId, userSpecificKey);
        Boolean wasSet = redisTemplate
                .opsForValue()
                .setIfAbsent(userSpecificKey, MAX_REQUESTS_PER_DURATION, RATE_LIMIT_DURATION);
        log.info("[End] Rate limit check completed for user {}.",userId);

        if (Boolean.TRUE.equals(!wasSet)) {

            log.error("[RateLimitException] Rate limit exceeded for user {}", userId);
            throw new RateLimitException("Rate limit exceeded for user %s. Please wait a moment.".formatted(userId));

        }
    }

}




