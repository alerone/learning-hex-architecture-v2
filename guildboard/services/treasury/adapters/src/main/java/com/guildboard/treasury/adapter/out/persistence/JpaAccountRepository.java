package com.guildboard.treasury.adapter.out.persistence;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.guildboard.treasury.application.port.out.AccountRepository;
import com.guildboard.treasury.domain.Account;
import com.guildboard.treasury.domain.AdventurerId;

import lombok.RequiredArgsConstructor;

/**
 * JpaAccountRepository
 */
@RequiredArgsConstructor 
@Transactional 
public class JpaAccountRepository implements AccountRepository {

    private final AccountJpaRepository jpa;

    @Override
    @Transactional(readOnly=true)
    public Optional<Account> findById(AdventurerId adventurerId) {
        return jpa.findById(adventurerId.value()).map(AccountPersistenceMapper::toDomain);
    }

    @Override
    public void save(Account account) {
        jpa.save(AccountPersistenceMapper.toEntity(account));
    }

    
}
