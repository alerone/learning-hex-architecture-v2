package com.guildboard.quests.adapter.out.rest;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import com.guildboard.quests.application.port.out.AdventurerDirectory;
import com.guildboard.quests.application.port.out.AdventurerUnavailableException;
import com.guildboard.quests.application.port.out.AdventurerView;
import com.guildboard.quests.domain.AdventurerId;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;

/**
 * RestAdventurerDirectory
 */
public class RestAdventurerDirectory implements AdventurerDirectory {
    private final RestClient client;
    private final CircuitBreaker breaker;
    private final Retry retry;

    public RestAdventurerDirectory(
            String baseUrl,
            Duration connectionTimeout, Duration readTimeout, 
            CircuitBreaker breaker,
            Retry retry) {
        this(AdventurerClientFactory.create(baseUrl, connectionTimeout, readTimeout),
            breaker, retry);
    }

    public RestAdventurerDirectory(
            RestClient client,
            CircuitBreaker breaker,
            Retry retry) {
        this.client = client;
        this.breaker = breaker;
        this.retry = retry;
    }

    @Override
    public Optional<AdventurerView> findById(AdventurerId id) {
        Supplier<Optional<AdventurerView>> call = () -> fetch(id);
        Supplier<Optional<AdventurerView>> protectedCall =
            Retry.decorateSupplier(retry, CircuitBreaker.decorateSupplier(breaker, call));
        try {
            return protectedCall.get();
        } catch (CallNotPermittedException e) {
            throw new AdventurerUnavailableException(
                    "adventurers circuit breaker is open", e);
        } catch (ResourceAccessException | HttpServerErrorException e) {
            throw new AdventurerUnavailableException("adventurers did not answer", e);
        }
    }

    private Optional<AdventurerView> fetch(AdventurerId id) {
        try {
            AdventurerPayload payload = client.get()
                .uri("/adventurers/{id}", id.value())
                .retrieve()
                .body(AdventurerPayload.class);
            return Optional.ofNullable(payload)
                .map(AdventurerPayloadMapper::toView);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }
}
