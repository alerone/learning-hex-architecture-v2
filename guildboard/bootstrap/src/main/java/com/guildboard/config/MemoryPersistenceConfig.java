package com.guildboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.guildboard.adapter.out.memory.InMemoryAdventurerRepository;
import com.guildboard.adapter.out.memory.InMemoryQuestRepository;
import com.guildboard.application.port.out.AdventurerRepository;
import com.guildboard.application.port.out.QuestRepository;



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

    @Bean
    public AdventurerRepository adventurerRepository() {
        return new InMemoryAdventurerRepository();
    }
}
