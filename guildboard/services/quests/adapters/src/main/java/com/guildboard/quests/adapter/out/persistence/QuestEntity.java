package com.guildboard.quests.adapter.out.persistence;

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
 * QuestEntity
 */
@Entity 
@Table(name = "quests")
@Getter 
@AllArgsConstructor 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "reward_gold", nullable = false)
    private int rewardGold;

    @Column(name = "required_rank", nullable = false)
    private String requiredRank;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "adventurer_id")
    private String adventurerId;

    @Column(name = "expires_at")
    private Instant expiresAt;



    
}
