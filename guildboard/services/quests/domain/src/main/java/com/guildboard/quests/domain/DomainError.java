package com.guildboard.quests.domain;

import java.time.Instant;

/**
 * DomainError
 */
public sealed interface DomainError {
    record QuestNotFound(QuestId id) implements DomainError {}
    record AdventurerNotFound(AdventurerId id) implements DomainError {}
    record InvalidTransition(QuestId id, QuestStatus actual,
            QuestStatus expected) implements DomainError {}
    record RankTooLow(Rank adventurerRank, Rank requiredRank) implements DomainError {}
    record TooManyActiveQuests(int active, int limit) implements DomainError {}
    record QuestNotPublished(QuestId id, QuestStatus actual) implements DomainError {}
    record QuestExpired(QuestId id, Instant expiresAt) implements DomainError {}

    default String code() {
        return switch(this) {
            case QuestNotFound e -> "QUEST_NOT_FOUND";
            case AdventurerNotFound e -> "ADVENTURER_NOT_FOUND";
            case InvalidTransition e -> "INVALID_TRANSITION";
            case RankTooLow e -> "RANK_TOO_LOW";
            case TooManyActiveQuests e -> "TOO_MANY_ACTIVE_QUESTS";
            case QuestNotPublished e -> "QUEST_NOT_PUBLISHED";
            case QuestExpired e -> "QUEST_EXPIRED";
        };
    }
}
