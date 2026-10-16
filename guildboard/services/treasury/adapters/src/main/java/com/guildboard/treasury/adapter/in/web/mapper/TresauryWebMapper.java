package com.guildboard.treasury.adapter.in.web.mapper;

import com.guildboard.treasury.adapter.in.web.dto.AccountResponse;
import com.guildboard.treasury.adapter.in.web.dto.PaymentResponse;
import com.guildboard.treasury.domain.Account;
import com.guildboard.treasury.domain.LedgerEntry;

/**
 * TresauryWebMapper
 */
public final class TresauryWebMapper {

    private TresauryWebMapper() {}

    public static PaymentResponse toResponse(LedgerEntry entry) {
        return new PaymentResponse(
                entry.id(),
                entry.questId().value(),
                entry.adventurerId().value(),
                entry.amount().coins(),
                entry.createdAt().toString());
    }

    public static AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getOwner().value(), account.getBalance().coins());
    }
    
}
