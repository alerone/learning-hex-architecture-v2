package com.guildboard.quests.adapter.out.messaging;

import org.springframework.kafka.core.KafkaTemplate;

import com.guildboard.quests.application.port.out.QuestEventPublisher;
import com.guildboard.quests.domain.AdventurerId;
import com.guildboard.quests.domain.QuestId;
import com.guildboard.quests.domain.Reward;

import lombok.RequiredArgsConstructor;

/**
 * KafkaQuestEventPublisher
 */
@RequiredArgsConstructor 
public class KafkaQuestEventPublisher implements QuestEventPublisher{

    private final KafkaTemplate<String, QuestCompleted> kafka;
    private final String topic;

    @Override
    public void questCompleted(
            QuestId questId, AdventurerId adventurerId, Reward reward) {
        QuestCompleted message = new QuestCompleted(
                questId.value(), adventurerId.value(), reward.gold());
        kafka.send(topic, questId.value(), message);

    }
}
