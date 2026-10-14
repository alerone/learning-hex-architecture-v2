package com.guildboard.treasury.domain;

import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Account
 */
@Getter 
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Account{

    @EqualsAndHashCode.Include
    private final AdventurerId owner;
    private Gold balance;

    private Account(AdventurerId owner) {
        this.owner = owner;
        this.balance = Gold.ZERO;
    }

    public static Account open(AdventurerId owner) {
        Objects.requireNonNull(owner, "owner");
        return new Account(owner);
    }

    public void credit(Gold amount) {
        if (amount.coins() <= 0) {
            throw new IllegalArgumentException(
                    "credit must be positive: " + amount.coins());
        }

        balance = balance.plus(amount);
    }

    
}
