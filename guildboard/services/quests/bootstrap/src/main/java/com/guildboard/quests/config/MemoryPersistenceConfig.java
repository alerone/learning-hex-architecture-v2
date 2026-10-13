package com.guildboard.quests.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.guildboard.quests.adapter.out.memory.InMemoryQuestRepository;
import com.guildboard.quests.application.port.out.QuestRepository;



/**
 * BeanConfig
 */
@Configuration
@Profile("!postgres")
public class MemoryPersistenceConfig {

    @Bean
    public QuestRepository questRepository() {
        return new InMemoryQuestRepository();
    }
}
