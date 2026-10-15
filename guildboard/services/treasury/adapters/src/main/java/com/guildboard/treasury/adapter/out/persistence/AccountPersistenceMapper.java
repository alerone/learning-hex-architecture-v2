package com.guildboard.treasury.adapter.out.persistence;

import com.guildboard.treasury.domain.Account;
import com.guildboard.treasury.domain.AdventurerId;
import com.guildboard.treasury.domain.Gold;

/**
 * AccountPersistenceMapper
 */
public final class AccountPersistenceMapper {

    private AccountPersistenceMapper() {}

    public static Account toDomain(AccountEntity entity) {
        Account ac = Account.open(new AdventurerId(entity.getAdventurerId()));
        if (entity.getBalance() > 0) {
            ac.credit(new Gold(entity.getBalance()));
        }
        return ac;
    }

    public static AccountEntity toEntity(Account ac) {
        return new AccountEntity(ac.getOwner().value(), ac.getBalance().coins());
    }
}
