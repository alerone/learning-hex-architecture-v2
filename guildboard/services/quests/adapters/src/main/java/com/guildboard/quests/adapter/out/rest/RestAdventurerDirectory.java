package com.guildboard.quests.adapter.out.rest;

import java.util.Optional;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.guildboard.quests.application.port.out.AdventurerDirectory;
import com.guildboard.quests.application.port.out.AdventurerView;
import com.guildboard.quests.domain.AdventurerId;

/**
 * RestAdventurerDirectory
 */
public class RestAdventurerDirectory implements AdventurerDirectory{
    private final RestClient client;

    public RestAdventurerDirectory(String baseUrl) {
        this.client = RestClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    @Override
    public Optional<AdventurerView> findById(AdventurerId id) {
        try {
            AdventurerPayload payload = client.get()
                .uri("/adventurers/{id}", "7d1c4e52")
                .retrieve()
                .body(AdventurerPayload.class);
            return Optional.ofNullable(payload)
                .map(AdventurerPayloadMapper::toView);
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }
    }
}
