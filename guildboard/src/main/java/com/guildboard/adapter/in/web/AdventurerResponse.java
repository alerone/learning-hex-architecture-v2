package com.guildboard.adapter.in.web;

import com.guildboard.domain.Adventurer;
import com.guildboard.domain.Rank;

/**
 * AdventurerResponse
 */
public record AdventurerResponse(String id, String name, Rank rank, int experience) { 
    public static AdventurerResponse from(Adventurer adventurer) {
        return new AdventurerResponse(adventurer.getId().value(),
                adventurer.getName(),
                adventurer.getRank(),
                adventurer.getExperience());
    }
}
