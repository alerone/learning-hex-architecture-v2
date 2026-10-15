package com.guildboard.adventurers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.guildboard.adventurers.adapter.out.memory.InMemoryAdventurerRepository;
import com.guildboard.adventurers.application.port.out.AdventurerRepository;



/**
 * BeanConfig
 */
@Configuration
@Profile("!postgres")
public class MemoryPersistenceConfig {

    @Bean
    public AdventurerRepository questRepository() {
        return new InMemoryAdventurerRepository();
    }
}
