package com.guildboard.quests.config;

import java.time.Clock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.guildboard.quests.adapter.out.notification.LogNotificationSender;
import com.guildboard.quests.adapter.out.rest.RestAdventurerDirectory;
import com.guildboard.quests.application.port.in.AcceptQuestUseCase;
import com.guildboard.quests.application.port.in.CompleteQuestUseCase;
import com.guildboard.quests.application.port.in.GetQuestUseCase;
import com.guildboard.quests.application.port.in.PublishQuestUseCase;
import com.guildboard.quests.application.port.out.AdventurerDirectory;
import com.guildboard.quests.application.port.out.NotificationSender;
import com.guildboard.quests.application.port.out.QuestEventPublisher;
import com.guildboard.quests.application.port.out.QuestRepository;
import com.guildboard.quests.application.service.AcceptQuestService;
import com.guildboard.quests.application.service.CompleteQuestService;
import com.guildboard.quests.application.service.GetQuestService;
import com.guildboard.quests.application.service.PublishQuestService;



/**
 * BeanConfig
 */
@Configuration
public class BeanConfig {

    @Bean
    public PublishQuestUseCase publishQuestUseCase(QuestRepository questRepository, Clock clock) {
        return new PublishQuestService(questRepository, clock);
    }

    @Bean
    public GetQuestUseCase getQuestUseCase(QuestRepository questRepository) {
        return new GetQuestService(questRepository);
    }

    @Bean
    public AcceptQuestUseCase acceptQuestUseCase(
            QuestRepository quests,
            AdventurerDirectory adventurers,
            Clock clock) {
        return new AcceptQuestService(quests, adventurers, clock);
    }

    @Bean
    public CompleteQuestUseCase completeQuestUseCase(
            QuestRepository quests,
            QuestEventPublisher publisher,
            NotificationSender notificationSender) {
        return new CompleteQuestService(quests, publisher, notificationSender);
    }

    @Bean 
    public AdventurerDirectory adventurerDirectory(
            @Value("${guildboard.adventurers.base-url}") String baseUrl) {
            return new RestAdventurerDirectory(baseUrl);
            }

    @Bean 
    public NotificationSender notificationSender() {
        return new LogNotificationSender();
    }

    @Bean 
    public Clock clock() {
        return Clock.systemUTC();
    }
}
