package com.guildboard.adventurers.adapter.out.persistence;

import com.guildboard.adventurers.domain.Adventurer;
import com.guildboard.adventurers.domain.AdventurerId;

/**
 * AdventurerPersistenceMapper
 */
final class AdventurerPersistenceMapper {

    private AdventurerPersistenceMapper() {}

    static AdventurerEntity toEntity(Adventurer adv) {
        return new AdventurerEntity(adv.getId().value(),
                adv.getName(),
                adv.getRank().name(),
                adv.getExperience());
    }

    static Adventurer toDomain(AdventurerEntity entity) {
        Adventurer adventurer = Adventurer.register(new AdventurerId(entity.getId()), entity.getName());

        if(entity.getExperience() > 0) {
            adventurer.gainExperience(entity.getExperience());
        }

        return adventurer;
    }
    
}
