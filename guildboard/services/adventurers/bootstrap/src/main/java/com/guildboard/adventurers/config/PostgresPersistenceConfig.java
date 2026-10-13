package com.guildboard.adventurers.config;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.guildboard.adventurers.adapter.out.persistence.JpaAdventurerRepository;
import com.guildboard.adventurers.adapter.out.persistence.AdventurerEntity;
import com.guildboard.adventurers.adapter.out.persistence.AdventurerJpaRepository;
import com.guildboard.adventurers.application.port.out.AdventurerRepository;



/**
 * BeanConfig
 */
@Configuration
@Profile("postgres")
@EnableJpaRepositories(basePackageClasses = AdventurerJpaRepository.class)
@EntityScan(basePackageClasses = AdventurerEntity.class)
public class PostgresPersistenceConfig {

    @Bean
    public AdventurerRepository questRepository(AdventurerJpaRepository repo) {
        return new JpaAdventurerRepository(repo);
    }

}
