package com.guildboard.treasury.config;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.guildboard.treasury.application.port.in.GetAccountUseCase;
import com.guildboard.treasury.application.port.in.GetLedgerBalanceUseCase;
import com.guildboard.treasury.application.port.in.PayRewardUseCase;
import com.guildboard.treasury.application.port.out.AccountRepository;
import com.guildboard.treasury.application.port.out.LedgerRepository;
import com.guildboard.treasury.application.service.GetAccountService;
import com.guildboard.treasury.application.service.GetLedgerBalanceService;
import com.guildboard.treasury.application.service.PayRewardService;


/**
 * BeanConfig
 */
@Configuration
public class BeanConfig {

    @Bean 
    public PayRewardUseCase payRewardUseCase(
            AccountRepository accounts, LedgerRepository ledgers, Clock clock) {
        return new PayRewardService(accounts, ledgers, clock);
    }

    @Bean 
    public GetAccountUseCase getAccountUseCase(
            AccountRepository accounts) {
        return new GetAccountService(accounts);
    }

    @Bean 
    public GetLedgerBalanceUseCase getLedgerBalanceUseCase(
            LedgerRepository ledgers) {
        return new GetLedgerBalanceService(ledgers);
    }

    @Bean 
    public Clock clock() {
        return Clock.systemUTC();
    }

}
