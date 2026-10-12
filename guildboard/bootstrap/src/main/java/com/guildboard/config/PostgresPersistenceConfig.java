package com.guildboard.config;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.guildboard.adapter.out.memory.InMemoryAdventurerRepository;
import com.guildboard.adapter.out.persistence.JpaQuestRepository;
import com.guildboard.adapter.out.persistence.QuestEntity;
import com.guildboard.adapter.out.persistence.QuestJpaRepository;
import com.guildboard.application.port.out.AdventurerRepository;
import com.guildboard.application.port.out.QuestRepository;



/**
 * BeanConfig
 */
@Configuration
@Profile("postgres")
@EnableJpaRepositories(basePackageClasses = QuestJpaRepository.class)
@EntityScan(basePackageClasses = QuestEntity.class)
public class PostgresPersistenceConfig {

    @Bean
    public QuestRepository questRepository(QuestJpaRepository repo) {
        return new JpaQuestRepository(repo);
    }

    @Bean
    public AdventurerRepository adventurerRepository() {
        return new InMemoryAdventurerRepository();
    }
}
