package com.guildboard.quests.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * QuestAcceptTest
 */
class QuestAcceptTest {

    private static final QuestId ID = new QuestId("q-1");
    private static final AdventurerId HERO = new AdventurerId("h-1");

    private static Quest publish(Rank rank) {
        Quest quest = Quest.draft(ID, "Domar al dragón", new Reward(150), rank);
        quest.publish();
        return quest;
    }

    @Test 
    void noviceAdventurerCantAcceptAdeptMissions() {
        Quest quest = publish(Rank.ADEPT);

        assertThatThrownBy(() -> quest.accept(HERO, Rank.NOVICE, 0))
            .isInstanceOfSatisfying(DomainException.class, e -> assertThat(
                        e.getError()).isEqualTo(new DomainError.RankTooLow(
                            Rank.NOVICE, quest.getRequiredRank())));
        assertThat(quest.getStatus()).isEqualTo(QuestStatus.PUBLISHED);
        assertThat(quest.getAssignedTo()).isEmpty();
    }

    @Test 
    void tryingToAcceptQuestWhileHavingThreeQuestsActive() {
        Quest quest = publish(Rank.NOVICE);

        int ACTIVE = 3;

        assertThatThrownBy(() -> quest.accept(HERO, Rank.NOVICE, ACTIVE))
            .isInstanceOfSatisfying(DomainException.class, e -> assertThat(
                        e.getError()).isEqualTo(new DomainError.TooManyActiveQuests(ACTIVE, 3)));
    }

    @Test 
    void adventurerCanAcceptWithOnlyTwoAcceptedMissions() {
        Quest quest = publish(Rank.NOVICE);

        int ACTIVE = 2;

        quest.accept(HERO, Rank.NOVICE, ACTIVE);
        assertThat(quest.getStatus()).isEqualTo(QuestStatus.ACCEPTED);
        assertThat(quest.getAssignedTo().get()).isEqualTo(HERO);
    }

    @Test 
    void cantAcceptANotPublishedQuest() {
        Quest quest = Quest.draft(ID, "Limpiar el salón", new Reward(150), Rank.NOVICE);

        assertThatThrownBy(() -> quest.accept(HERO, Rank.NOVICE, 0))
            .isInstanceOfSatisfying(
                    DomainException.class, e -> assertThat(e.getError()).isEqualTo(
                        new DomainError.QuestNotPublished(quest.getId(), quest.getStatus())));
    }
    
}
