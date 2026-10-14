package com.guildboard.treasury.domain;

/**
 * Gold
 */
public record Gold(int coins) {
    public static final Gold ZERO = new Gold(0);

    public Gold {
        if (coins < 0) {
            throw new IllegalArgumentException(
                    "gold must not be negative: " + coins);
        }
    }

    public Gold plus(Gold other) {
        return new Gold(Math.addExact(coins, other.coins));
    }
}
