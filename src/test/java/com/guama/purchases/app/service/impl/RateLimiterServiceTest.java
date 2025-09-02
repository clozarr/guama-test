package com.guama.purchases.app.service.impl;

import com.guama.purchases.shared.exception.RateLimitException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RateLimiterServiceTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private RateLimiterService rateLimiterService;

    private static final String USER_ID = "test-user";

    @Test
    void checkRateLimit_WhenWithinLimit_ShouldNotThrowException() {

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.setIfAbsent(anyString(), anyString(), any(Duration.class))).thenReturn(true);


        assertDoesNotThrow(() -> rateLimiterService.checkRateLimit(USER_ID));
    }

    @Test
    void checkRateLimit_WhenLimitExceeded_ShouldThrowRateLimitException() {

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.setIfAbsent(anyString(), anyString(), any(Duration.class))).thenReturn(false);


        RateLimitException exception = assertThrows(RateLimitException.class, () -> {
            rateLimiterService.checkRateLimit(USER_ID);
        });

        assertTrue(exception.getMessage().contains("Rate limit exceeded for user"));
    }

    @Test
    void checkRateLimit_WhenUserIdIsNull_ShouldThrowIllegalArgumentException() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rateLimiterService.checkRateLimit(null);
        });

        assertEquals("User ID cannot be empty for rate limiting.", exception.getMessage());
    }

    @Test
    void checkRateLimit_WhenUserIdIsBlank_ShouldThrowIllegalArgumentException() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rateLimiterService.checkRateLimit(" ");
        });

        assertEquals("User ID cannot be empty for rate limiting.", exception.getMessage());
    }
}
