package com.guildboard.domain;

import java.util.Objects;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Quest
 */
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Quest {
    public static final int MAX_QUESTS = 3;

    @EqualsAndHashCode.Include
    private final QuestId id;
    private final String title;
    private final Reward reward;
    private final Rank requiredRank;
    private QuestStatus status;
    private AdventurerId assignedTo;

    private Quest(QuestId id, String title, Reward reward, Rank requiredRank) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(requiredRank, "requiredRank");
        Objects.requireNonNull(reward, "reward");
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be blank");
        }
        this.id = id;
        this.title = title;
        this.reward = reward;
        this.requiredRank = requiredRank;
        this.status = QuestStatus.DRAFT;
    }

    public static Quest draft(QuestId id, String title, Reward reward, Rank requiredRank) {
        return new Quest(id, title, reward, requiredRank);
    } 

    public void publish() {
        requireStatus(QuestStatus.DRAFT);
        status = QuestStatus.PUBLISHED;
    }

    public void accept(AdventurerId adventurerId, Rank adventurerRank, int activeQuests) {
        Objects.requireNonNull(adventurerRank, "adventurerRank");
        Objects.requireNonNull(activeQuests, "activeQuests");
        if (activeQuests < 0) {
            throw new IllegalArgumentException("activeQuests must not be negative: " + activeQuests);
        }
        requireStatus(QuestStatus.PUBLISHED);
        assignedTo = Objects.requireNonNull(adventurerId, "adventurerId");
        status = QuestStatus.ACCEPTED;
    }

    public void complete() {
        requireStatus(QuestStatus.ACCEPTED);
        status = QuestStatus.COMPLETED;
    }

    public Optional<AdventurerId> getAssignedTo() {
        return Optional.ofNullable(assignedTo);
    }

    private void requireStatus(QuestStatus expected) {
        if (status != expected) {
            throw new DomainException(new DomainError.InvalidTransition(id, status, expected));
        }
    }
    
}
