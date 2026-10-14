package com.guildboard.treasury.adapter.in.web.mapper;

import com.guildboard.treasury.adapter.in.web.dto.AccountResponse;
import com.guildboard.treasury.adapter.in.web.dto.PayRewardRequest;
import com.guildboard.treasury.adapter.in.web.dto.PaymentResponse;
import com.guildboard.treasury.application.port.in.PayRewardCommand;
import com.guildboard.treasury.domain.Account;
import com.guildboard.treasury.domain.AdventurerId;
import com.guildboard.treasury.domain.Gold;
import com.guildboard.treasury.domain.LedgerEntry;
import com.guildboard.treasury.domain.QuestId;

/**
 * TresauryWebMapper
 */
public final class TresauryWebMapper {

    private TresauryWebMapper() {}

    public static PayRewardCommand toCommand(PayRewardRequest rq) {
        return new PayRewardCommand(new QuestId(rq.questId()),
                new AdventurerId(rq.adventurerId()),
                new Gold(rq.amount()));
    }

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
