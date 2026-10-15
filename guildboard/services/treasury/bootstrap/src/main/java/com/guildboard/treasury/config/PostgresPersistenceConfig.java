package com.guildboard.treasury.config;


import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.guildboard.treasury.adapter.out.persistence.AccountEntity;
import com.guildboard.treasury.adapter.out.persistence.AccountJpaRepository;
import com.guildboard.treasury.adapter.out.persistence.JpaAccountRepository;
import com.guildboard.treasury.adapter.out.persistence.JpaLedgerRepository;
import com.guildboard.treasury.adapter.out.persistence.LedgerJpaRepository;
import com.guildboard.treasury.application.port.out.AccountRepository;
import com.guildboard.treasury.application.port.out.LedgerRepository;


/**
 * BeanConfig
 */
@Configuration
@Profile("postgres")
@EnableJpaRepositories(basePackageClasses = AccountJpaRepository.class)
@EntityScan(basePackageClasses = AccountEntity.class)
public class PostgresPersistenceConfig {

    @Bean
    public LedgerRepository ledgerRepository(LedgerJpaRepository repo) {
        return new JpaLedgerRepository(repo);
    }

    @Bean  
    public AccountRepository accountRepository(AccountJpaRepository repo) {
        return new JpaAccountRepository(repo);
    }
}
