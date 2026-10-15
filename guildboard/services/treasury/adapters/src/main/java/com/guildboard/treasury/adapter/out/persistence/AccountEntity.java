package com.guildboard.treasury.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * AccountEntity
 */
@Entity 
@Table(name = "accounts")
@Getter 
@AllArgsConstructor  
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountEntity {


    @Id 
    @Column(name = "adventurer_id")
    private String adventurerId;

    @Column(name = "balance")
    private int balance;
}
