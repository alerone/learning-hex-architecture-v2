package com.guildboard.treasury.adapter.out.persistence;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * LedgerEntryEntity
 */
@Entity 
@Table(name="ledger_entry")
@Getter 
@AllArgsConstructor 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LedgerEntryEntity {

    @Id 
    @Column(name = "id")
    private String id;

    @Column(name = "quest_id", nullable = false)
    private String questId;

    @Column(name = "adventurer_id", nullable = false)
    private String adventurerId;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
