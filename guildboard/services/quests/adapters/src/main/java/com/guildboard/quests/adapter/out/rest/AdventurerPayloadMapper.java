package com.guildboard.quests.adapter.out.rest;

import com.guildboard.quests.application.port.out.AdventurerView;
import com.guildboard.quests.domain.AdventurerId;
import com.guildboard.quests.domain.Rank;

/**
 * AdventurerPayloadMapper
 */
public final class AdventurerPayloadMapper {

    private AdventurerPayloadMapper() {}

    static AdventurerView toView(AdventurerPayload payload) {
        if(payload.id() == null | payload.rank() == null) {
            throw new IllegalStateException(
                    "incomplete adventurer from adventures service: id="
                    + payload.id() + ", rank=" + payload.rank());
        }

        return new AdventurerView(new AdventurerId(payload.id()), 
                parseRank(payload.rank()));
    }

    private static Rank parseRank(String value) {
        try {
            return Rank.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("unknown rank from adventurers service: " + value, e);
        }
    }
    
}
