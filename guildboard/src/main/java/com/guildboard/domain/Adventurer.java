package com.guildboard.domain;

import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Adventurer
 */
@Getter 
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Adventurer {
    @EqualsAndHashCode.Include
    private final AdventurerId id;
    private String name;
    private Rank rank;
    private int experience;

    private Adventurer(AdventurerId id, String name) {
        this.id = id;
        this.name = name;
        this.rank = Rank.NOVICE;
        this.experience = 0;
    }

    public static Adventurer register(AdventurerId id, String name) {
        Objects.requireNonNull(id, "id");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name must not be blank");
        return new Adventurer(id, name);
    }

    public void gainExperience(int points) {
        if (points <= 0) throw new IllegalArgumentException("points must be positive: " + points);
        experience += points;
    }    
}
