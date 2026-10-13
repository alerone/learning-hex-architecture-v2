package com.guildboard.adventurers.application.service;

import com.guildboard.adventurers.application.port.in.GrantExperienceCommand;
import com.guildboard.adventurers.application.port.in.GrantExperienceUseCase;
import com.guildboard.adventurers.application.port.out.AdventurerRepository;
import com.guildboard.adventurers.domain.Adventurer;
import com.guildboard.adventurers.domain.DomainError;
import com.guildboard.adventurers.domain.DomainException;

import lombok.RequiredArgsConstructor;

/**
 * GrantExperienceService
 */
@RequiredArgsConstructor 
public class GrantExperienceService implements GrantExperienceUseCase {

    private final AdventurerRepository adventurerRepository;

    @Override
    public Adventurer grant(GrantExperienceCommand command) {
        Adventurer adventurer = adventurerRepository.findById(command.adventurer()).orElseThrow(() ->
                new DomainException(new DomainError.AdventurerNotFound(command.adventurer())));

        adventurer.gainExperience(command.points());
        adventurerRepository.save(adventurer);
        return adventurer;
    }
}
