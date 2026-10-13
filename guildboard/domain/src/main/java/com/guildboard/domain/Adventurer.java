package com.guildboard.domain;

import java.util.Map;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Adventurer
 */
@Getter 
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Adventurer {

    private static final Map<Rank, Integer> THRESHOLDS = Map.of(
            Rank.NOVICE, 0,
            Rank.ADEPT, 100,
            Rank.VETERAN, 300);

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

    public Rank gainExperience(int points) {
        if (points <= 0) throw new IllegalArgumentException("points must be positive: " + points);
        
        experience += points;
        Rank reached = rankFor(experience);
        if (reached.compareTo(rank) > 0) {
            rank = reached;
        }
        return rank;
    }    

    private static Rank rankFor(int experience) {
        Rank result = Rank.NOVICE;
        for(Rank candidate: Rank.values()) {
            if (experience >= THRESHOLDS.get(candidate)) {
                result = candidate;
            }
        }
        return result;
    }
}
