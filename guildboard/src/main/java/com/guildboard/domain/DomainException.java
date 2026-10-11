package com.guildboard.domain;

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
            case DomainError.QuestNotFound(QuestId id) -> 
                "quest not found: " + id.value();
            case DomainError.AdventurerNotFound(AdventurerId id) -> 
                "adventurer not found: " + id.value();
            case DomainError.InvalidTransition(QuestId id, QuestStatus actual, QuestStatus expected) -> 
                "quest " + id.value() + " is " + actual + ", expected " + expected;
            case DomainError.RankTooLow(Rank adventurerRank, Rank requiredRank) -> 
                "rank" + adventurerRank + " cannot take a quest that requires " + requiredRank;
            case DomainError.TooManyActiveQuests(int active, int limit) -> 
                "adventurer has " + active 
                + " active quests, the limit is " + limit;
            case DomainError.QuestNotPublished(QuestId id, QuestStatus actual) ->
                "quest " + id.value() + " status is: " + actual + " and status must be " + 
                QuestStatus.PUBLISHED;
        };
    }

    
}
