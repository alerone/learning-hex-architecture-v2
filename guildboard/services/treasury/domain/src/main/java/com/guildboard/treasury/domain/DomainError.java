package com.guildboard.treasury.domain;

/**
 * DomainError
 */
public sealed interface DomainError {

    record QuestAlreadyPaid(QuestId questId) implements DomainError {}

    default String code() {
        return switch (this) {
            case QuestAlreadyPaid e -> "QUEST_ALREADY_PAID";
        };
    }

    
}
