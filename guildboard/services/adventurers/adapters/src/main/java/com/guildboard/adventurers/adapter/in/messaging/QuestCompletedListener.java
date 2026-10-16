package com.guildboard.adventurers.adapter.in.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.guildboard.adventurers.application.port.in.GrantExperienceCommand;
import com.guildboard.adventurers.application.port.in.GrantExperienceUseCase;
import com.guildboard.adventurers.domain.AdventurerId;

import lombok.RequiredArgsConstructor;

/**
 * QuestCompletedListener
 */
@Component
@RequiredArgsConstructor
public class QuestCompletedListener {

    private final GrantExperienceUseCase grantExperience;

    @KafkaListener(topics = "${guildboard.messaging.topic}", groupId = "adventurers")
    public void onQuestCompleted(QuestCompleted message) {
        System.out.println("HOOOOLAAA");
        GrantExperienceCommand command = new GrantExperienceCommand(
                new AdventurerId(message.adventurerId()),
                message.reward());
        grantExperience.grant(command);
    }
}
