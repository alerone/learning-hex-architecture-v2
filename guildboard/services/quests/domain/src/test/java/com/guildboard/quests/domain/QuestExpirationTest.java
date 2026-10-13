package com.guildboard.quests.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

/**
 * QuestExpirationTest
 */
class QuestExpirationTest {
    private static final QuestId ID = new QuestId("q-1");
    private static final String DEADLINE = "2026-10-10T12:00:00Z";

    private static Quest publish() {
        Quest quest = Quest.draft(ID, "Domar al dragón", new Reward(150), Rank.ADEPT, clockAt(DEADLINE).instant());
        quest.publish();
        return quest;
    }
    

    @Test 
    void aSecondBeforeDeadlineIsExpiredIsFalse() {
        Clock clock = clockAt("2026-10-10T11:59:59Z");
        Quest quest = publish();
        assertThat(quest.isExpired(clock)).isFalse();
    }

    @Test 
    void exactlyAtDeadlineIsExpiredIsTrue() {
        Clock clock = clockAt(DEADLINE);
        Quest quest = publish();
        assertThat(quest.isExpired(clock)).isTrue();
    }

    @Test 
    void aSecondAfterDeadlineIsExpiredIsTrue() {
        Clock clock = clockAt("2026-10-10T12:00:01Z");
        Quest quest = publish();
        assertThat(quest.isExpired(clock)).isTrue();
    }

    @Test
    void noDeadlineQuestIsExpiredIsAlwaysFalse() {
        Clock clock = clockAt("2999-10-10T12:00:00Z");
        Quest quest = Quest.draft(ID, "domar al dragón", new Reward(10), Rank.ADEPT);
        assertThat(quest.isExpired(clock)).isFalse();
    }

    @Test 
    void requireNotExpiredThrowsExceptionWhenDeadlineIsPassed() {
        Clock clock = clockAt(DEADLINE);
        Quest quest = publish();
        assertThatThrownBy(() -> quest.requireNotExpired(clock)).isInstanceOfSatisfying(DomainException.class, e ->
                assertThat(e.getError()).isEqualTo(
                    new DomainError.QuestExpired(
                        ID, clock.instant())));
    }
    
    private static Clock clockAt(String hour) {
        return Clock.fixed(Instant.parse(hour), ZoneOffset.UTC);
    }
}
