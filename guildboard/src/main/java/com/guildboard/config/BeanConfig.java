package com.guildboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.guildboard.adapter.out.memory.InMemoryQuestRepository;
import com.guildboard.application.port.in.GetQuestUseCase;
import com.guildboard.application.port.in.PublishQuestUseCase;
import com.guildboard.application.port.out.QuestRepository;
import com.guildboard.application.service.GetQuestService;
import com.guildboard.application.service.PublishQuestService;

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
    public PublishQuestUseCase publishQuestUseCase(QuestRepository questRepository) { 
        return new PublishQuestService(questRepository);
    }

    @Bean 
    public GetQuestUseCase getQuestUseCase(QuestRepository questRepository) {
        return new GetQuestService(questRepository);
    }
}
