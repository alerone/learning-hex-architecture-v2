package com.guildboard.quests.domain;

/**
 * Reward
 */
public record Reward(int gold) {
    public static final Reward ZERO = new Reward(0);

    public Reward {
        if (gold < 0) {
            throw new IllegalArgumentException("gold must not be negative: " + gold);
        }
    }

    public Reward plus(Reward other) {
        return new Reward(gold + other.gold());
    }
}
