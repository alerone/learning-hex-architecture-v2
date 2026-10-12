package com.guildboard.config;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.guildboard.adapter.out.memory.InMemoryAdventurerRepository;
import com.guildboard.adapter.out.memory.InMemoryQuestRepository;
import com.guildboard.adapter.out.notification.LogNotificationSender;
import com.guildboard.application.port.in.AcceptQuestUseCase;
import com.guildboard.application.port.in.CompleteQuestUseCase;
import com.guildboard.application.port.in.GetAdventurerUseCase;
import com.guildboard.application.port.in.GetQuestUseCase;
import com.guildboard.application.port.in.GrantExperienceUseCase;
import com.guildboard.application.port.in.PublishQuestUseCase;
import com.guildboard.application.port.in.RegisterAdventurerUseCase;
import com.guildboard.application.port.out.AdventurerRepository;
import com.guildboard.application.port.out.NotificationSender;
import com.guildboard.application.port.out.QuestRepository;
import com.guildboard.application.service.AcceptQuestService;
import com.guildboard.application.service.CompleteQuestService;
import com.guildboard.application.service.GetAdventurerService;
import com.guildboard.application.service.GetQuestService;
import com.guildboard.application.service.GrantExperienceService;
import com.guildboard.application.service.PublishQuestService;
import com.guildboard.application.service.RegisterAdventurerService;



/**
 * BeanConfig
 */
@Configuration
public class BeanConfig {

    @Bean
    public QuestRepository questRepository() {
        return new InMemoryQuestRepository();
    }

    @Bean
    public PublishQuestUseCase publishQuestUseCase(QuestRepository questRepository, Clock clock) {
        return new PublishQuestService(questRepository, clock);
    }

    @Bean
    public GetQuestUseCase getQuestUseCase(QuestRepository questRepository) {
        return new GetQuestService(questRepository);
    }

    @Bean
    public AdventurerRepository adventurerRepository() {
        return new InMemoryAdventurerRepository();
    }

    @Bean
    public GrantExperienceUseCase grantExperienceUseCase(
            AdventurerRepository adventurerRepository) {
        return new GrantExperienceService(adventurerRepository);
    }

    @Bean
    public AcceptQuestUseCase acceptQuestUseCase(
            QuestRepository quests,
            AdventurerRepository adventurers,
            Clock clock) {
        return new AcceptQuestService(quests, adventurers, clock);
    }

    @Bean
    public CompleteQuestUseCase completeQuestUseCase(
            QuestRepository quests,
            GrantExperienceUseCase grantExperienceUseCase,
            NotificationSender notificationSender) {
        return new CompleteQuestService(quests, grantExperienceUseCase, notificationSender);
    }

    @Bean 
    public GetAdventurerUseCase getAdventurerUseCase(AdventurerRepository repo) {
        return new GetAdventurerService(repo);
    }

    @Bean 
    public RegisterAdventurerUseCase registerAdventurerUseCase(AdventurerRepository repo) {
        return new RegisterAdventurerService(repo);
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
