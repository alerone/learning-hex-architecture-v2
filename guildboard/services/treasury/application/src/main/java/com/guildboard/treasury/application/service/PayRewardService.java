package com.guildboard.treasury.application.service;

import java.time.Clock;
import java.util.UUID;

import com.guildboard.treasury.application.port.in.PayRewardCommand;
import com.guildboard.treasury.application.port.in.PayRewardUseCase;
import com.guildboard.treasury.application.port.out.AccountRepository;
import com.guildboard.treasury.application.port.out.LedgerRepository;
import com.guildboard.treasury.domain.Account;
import com.guildboard.treasury.domain.DomainError;
import com.guildboard.treasury.domain.DomainException;
import com.guildboard.treasury.domain.Gold;
import com.guildboard.treasury.domain.LedgerEntry;

import lombok.RequiredArgsConstructor;

/**
 * PayRewardService
 */
@RequiredArgsConstructor 
public class PayRewardService implements PayRewardUseCase {

    private final AccountRepository accounts;
    private final LedgerRepository ledgers;
    private final Clock clock;

    @Override
    public LedgerEntry pay(PayRewardCommand command) {
        if (ledgers.existsByQuestId(command.questId())) {
            throw new DomainException(new DomainError.QuestAlreadyPaid(command.questId()));
        }
        LedgerEntry entry = new LedgerEntry(
                UUID.randomUUID().toString(),
                command.questId(), command.adventurerId(), command.amount(), clock.instant());
        ledgers.save(entry);
        Account account = accounts.findById(command.adventurerId())
            .orElseGet(() -> Account.open(command.adventurerId()));
        account.credit(new Gold(command.amount().coins()));
        accounts.save(account);
        return entry;
    }
}
