package com.guildboard.treasury.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.guildboard.treasury.adapter.out.memory.InMemoryAccountRepository;
import com.guildboard.treasury.adapter.out.memory.InMemoryLedgerRepository;
import com.guildboard.treasury.application.port.out.AccountRepository;
import com.guildboard.treasury.application.port.out.LedgerRepository;

/**
 * MemoryPersistenceConfig
 */
@Configuration
@Profile("!postgres")
public class MemoryPersistenceConfig {

    @Bean
    public LedgerRepository ledgerRepository() {
        return new InMemoryLedgerRepository();
    }

    @Bean  
    public AccountRepository accountRepository() {
        return new InMemoryAccountRepository();
    }
}
