package com.guildboard.adventurers.domain;

/**
 * DomainException
 */
public final class DomainException extends RuntimeException {
    private final DomainError error;

    public DomainException(DomainError error) { super(describe(error)); this.error = error; }

    public DomainError getError() {
        return error;
    }

    public static String describe(DomainError error) {
        return switch(error) {
            case DomainError.AdventurerNotFound(AdventurerId id) -> 
                "adventurer not found: " + id.value();
        };
    }

    
}
