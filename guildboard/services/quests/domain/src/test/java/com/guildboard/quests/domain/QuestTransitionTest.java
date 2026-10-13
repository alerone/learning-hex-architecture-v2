package com.guildboard.quests.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * QuestTransitionTest
 */
class QuestTransitionTest {

    private static final QuestId ID = new QuestId("q-1");
    private static final AdventurerId HERO = new AdventurerId("h-1");

    private static Quest draft() {
        return Quest.draft(ID, "Limpiar el sótano", new Reward(150), Rank.NOVICE);
    }

    @Test 
    void publishedQuestIsAcceptedByQualifiedAdventurer() {
        Quest quest = draft();
        quest.publish();
        quest.accept(HERO, Rank.NOVICE, 0);
        assertThat(quest.getStatus()).isEqualTo(QuestStatus.ACCEPTED);
        assertThat(quest.getAssignedTo()).contains(HERO);
    }

    @Test 
    void cannotPublishTwice() {
        Quest quest = draft();
        quest.publish();
        assertThatThrownBy(quest::publish).isInstanceOfSatisfying(DomainException.class, e ->
                assertThat(e.getError()).isEqualTo(
                    new DomainError.InvalidTransition(
                        ID, QuestStatus.PUBLISHED, QuestStatus.DRAFT)));
    }

    
}
