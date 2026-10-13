package com.guildboard.quests.domain;

/**
 * Rank
 */
public enum Rank {

    NOVICE, ADEPT, VETERAN;
    public boolean canTake(Rank required) {
        return this.compareTo(required) >= 0;
    }
}
