package com.guildboard.adventurers.adapter.in.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guildboard.adventurers.adapter.in.web.dto.AdventurerResponse;
import com.guildboard.adventurers.adapter.in.web.dto.GrantExperienceRequest;
import com.guildboard.adventurers.adapter.in.web.dto.RegisterAdventurerRequest;
import com.guildboard.adventurers.adapter.in.web.mapper.AdventurerWebMapper;
import com.guildboard.adventurers.application.port.in.GetAdventurerUseCase;
import com.guildboard.adventurers.application.port.in.GrantExperienceCommand;
import com.guildboard.adventurers.application.port.in.GrantExperienceUseCase;
import com.guildboard.adventurers.application.port.in.RegisterAdventurerUseCase;
import com.guildboard.adventurers.domain.Adventurer;
import com.guildboard.adventurers.domain.AdventurerId;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * AdventurerController
 */
@RestController()
@RequestMapping("/adventurers")
@RequiredArgsConstructor 
public class AdventurerController {

    private final GetAdventurerUseCase getAdventurer;
    private final RegisterAdventurerUseCase registerAdventurer;
    private final GrantExperienceUseCase grantExperience;

    @GetMapping("/{id}")
    ResponseEntity<AdventurerResponse> getById(@PathVariable("id") String id) {
        return getAdventurer.get(new AdventurerId(id))
            .map(AdventurerWebMapper::toResponse)
            .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    ResponseEntity<AdventurerResponse> register(
            @Valid @RequestBody RegisterAdventurerRequest rq) {
        Adventurer adventurer = registerAdventurer.register(
                AdventurerWebMapper.toCommand(rq));
        return ResponseEntity.created(URI.create("/adventurers/" + adventurer.getId().value()))
            .body(AdventurerWebMapper.toResponse(adventurer));
    }

    @PostMapping("/{id}/experience")
    public AdventurerResponse grantExperience(
            @PathVariable("id") String id,
            @Valid @RequestBody GrantExperienceRequest rq) {
            Adventurer adventurer = grantExperience.grant(
                    new GrantExperienceCommand(new AdventurerId(id), rq.points()));
            return AdventurerWebMapper.toResponse(adventurer);
            }

}
