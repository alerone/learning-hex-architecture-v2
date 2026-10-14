package com.guildboard.treasury.config;


import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.guildboard.treasury.adapter.out.persistence.JpaLedgerRepository;
import com.guildboard.treasury.adapter.out.persistence.LedgerEntryEntity;
import com.guildboard.treasury.adapter.out.persistence.LedgerJpaRepository;
import com.guildboard.treasury.application.port.out.LedgerRepository;


/**
 * BeanConfig
 */
@Configuration
@Profile("postgres")
@EnableJpaRepositories(basePackageClasses = {LedgerJpaRepository.class, })
@EntityScan(basePackageClasses = {LedgerEntryEntity.class})
public class PostgresPersistenceConfig {

    @Bean
    public LedgerRepository questRepository(LedgerJpaRepository repo) {
        return new JpaLedgerRepository(repo);
    }

}
