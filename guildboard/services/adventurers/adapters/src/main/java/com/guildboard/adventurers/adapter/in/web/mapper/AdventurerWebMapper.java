package com.guildboard.adventurers.adapter.in.web.mapper;

import com.guildboard.adventurers.adapter.in.web.dto.AdventurerResponse;
import com.guildboard.adventurers.adapter.in.web.dto.RegisterAdventurerRequest;
import com.guildboard.adventurers.application.port.in.RegisterAdventurerCommand;
import com.guildboard.adventurers.domain.Adventurer;

/**
 * AdventurerWebMapper
 */
public final class AdventurerWebMapper {

    private AdventurerWebMapper() {
    }

    public static RegisterAdventurerCommand toCommand(
            RegisterAdventurerRequest request) {
        return new RegisterAdventurerCommand(request.name());
    }

    public static AdventurerResponse toResponse(Adventurer adventurer) {
        return new AdventurerResponse(
                adventurer.getId().value(),
                adventurer.getName(),
                adventurer.getRank(),
                adventurer.getExperience());
    }

}
