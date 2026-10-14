package com.guildboard.treasury.domain;

/**
 * DomainException
 */
public final class DomainException extends RuntimeException {

    private final DomainError error;

    public DomainException(DomainError error) {
        super(describe(error));
        this.error = error;
    }

    public DomainError getError() {
        return error;
    }    

    private static String describe(DomainError error) {
        return switch(error) {
            case DomainError.QuestAlreadyPaid(QuestId id) -> 
                "quest already paid: " + id.value();
        };
    }
    
}
