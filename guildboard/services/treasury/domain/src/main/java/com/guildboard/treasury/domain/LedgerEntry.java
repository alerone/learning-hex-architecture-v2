package com.guildboard.treasury.domain;

import java.time.Instant;
import java.util.Objects;

/**
 * LedgerEntry
 */
public record LedgerEntry(
        String id,
        QuestId questId,
        AdventurerId adventurerId,
        Gold amount,
        Instant createdAt) {

    public LedgerEntry {
        if (id == null || id.isBlank()) 
            throw new IllegalArgumentException("id must not be blank");
        Objects.requireNonNull(questId, "questId");
        Objects.requireNonNull(adventurerId, "adventurerId");
        Objects.requireNonNull(amount, "amount");
        Objects.requireNonNull(createdAt, "createdAt");
        if(amount.coins() <= 0) {
            throw new IllegalArgumentException(
                    "a payment must be positive: " + amount.coins());
        }
    }
}
