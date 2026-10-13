package com.guildboard.adventurers.domain;

/**
 * DomainError
 */
public sealed interface DomainError {
    record AdventurerNotFound(AdventurerId id) implements DomainError {}

    default String code() {
        return switch(this) {
            case AdventurerNotFound e -> "ADVENTURER_NOT_FOUND";
        };
    }
}
