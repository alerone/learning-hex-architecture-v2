package com.guildboard.quests.adapter.out.rest;
/**
 * RestAdventurerDirectoryBreakerTest
 */

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import com.guildboard.quests.application.port.out.AdventurerUnavailableException;
import com.guildboard.quests.domain.AdventurerId;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import tools.jackson.databind.jsontype.impl.AsExternalTypeSerializer;

public class RestAdventurerDirectoryBreakerTest {
    private static final AdventurerId LUCIA = new AdventurerId("lucia");
    private CircuitBreaker breaker;
    private RestAdventurerDirectory directory;

    @BeforeEach 
    void setUp() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .slidingWindowType(SlidingWindowType.COUNT_BASED)
            .slidingWindowSize(4)
            .minimumNumberOfCalls(4)
            .failureRateThreshold(50)
            .waitDurationInOpenState(Duration.ofMinutes(1))
            .permittedNumberOfCallsInHalfOpenState(2)
            .recordExceptions(ResourceAccessException.class)
            .build();
        breaker = CircuitBreaker.of("adventurers-test", config);
        Retry oneAttempt = Retry.of("adventurers-test", 
                RetryConfig.custom().maxAttempts(1).build());
        RestClient closedPort = AdventurerClientFactory.create("http://localhost:1",
                Duration.ofMillis(500), Duration.ofMillis(500));

        directory = new RestAdventurerDirectory(closedPort, breaker, oneAttempt);
    }

    private void callAndFail() {
        assertThatThrownBy(() -> directory.findById(LUCIA))
            .isInstanceOf(AdventurerUnavailableException.class)
            .hasCauseInstanceOf(ResourceAccessException.class);
    }


    @Test 
    void staysClosedBeforeTheMinimumNumberOfCalls() {
        callAndFail();
        callAndFail();
        callAndFail();

        assertThat(breaker.getState()).isEqualTo(CircuitBreaker.State.CLOSED);
        assertThat(breaker.getMetrics().getNumberOfFailedCalls()).isEqualTo(3);
    }

    @Test 
    void opensAfterFourFailuresAndRejectsTheNextCall() {
        for (int i = 0; i < 4; i++) {
            callAndFail();
        }

        assertThat(breaker.getState()).isEqualTo(CircuitBreaker.State.OPEN);
        assertThatThrownBy(() -> directory.findById(LUCIA))
            .isInstanceOf(AdventurerUnavailableException.class)
            .hasCauseInstanceOf(CallNotPermittedException.class);
        assertThat(breaker.getMetrics().getNumberOfNotPermittedCalls()).isEqualTo(1);
    }

    @Test 
    void opensAgainWhenTheTrialCallsFail() {
        for (int i = 0; i < 4; i++) {
            callAndFail();
        }

        breaker.transitionToHalfOpenState();
        assertThat(breaker.getState()).isEqualTo(CircuitBreaker.State.HALF_OPEN);


        callAndFail();
        callAndFail();

        assertThat(breaker.getState()).isEqualTo(CircuitBreaker.State.OPEN);
    }
}
