package com.guildboard.quests.adapter.out.persistence;

import com.guildboard.quests.domain.AdventurerId;
import com.guildboard.quests.domain.Quest;
import com.guildboard.quests.domain.QuestId;
import com.guildboard.quests.domain.QuestStatus;
import com.guildboard.quests.domain.Rank;
import com.guildboard.quests.domain.Reward;

/**
 * QuestPersistenceMapper
 */
final class QuestPersistenceMapper {

    private QuestPersistenceMapper() {}

    static QuestEntity toEntity(Quest quest) {
        return new QuestEntity(
                quest.getId().value(),
                quest.getTitle(),
                quest.getReward().gold(),
                quest.getRequiredRank().name(),
                quest.getStatus().name(),
                quest.getAssignedTo().map(AdventurerId::value).orElse(null),
                quest.getExpiresAt().orElse(null));
    }    

    static Quest toDomain(QuestEntity entity) {
        Quest quest = Quest.draft(
                new QuestId(entity.getId()),
                entity.getTitle(),
                new Reward(entity.getRewardGold()), 
                Rank.valueOf(entity.getRequiredRank()),
                entity.getExpiresAt());

        QuestStatus status = QuestStatus.valueOf(entity.getStatus());

        switch(status) {
            case DRAFT ->  {}
            case PUBLISHED ->  quest.publish();
            case ACCEPTED ->  restoreAccepted(quest, entity);
            case COMPLETED ->  {
                restoreAccepted(quest, entity);
                quest.complete();
            }
        };
        return quest;
    }

    private static void restoreAccepted(Quest quest, QuestEntity entity) {
        if (entity.getAdventurerId() == null) {
            throw new IllegalArgumentException("quest " + entity.getId()
                    + " is " + entity.getStatus() + " without adventurer_id");
        } 

        quest.publish();
        quest.accept(new AdventurerId(entity.getAdventurerId()), Rank.VETERAN, 0);
    }
}
