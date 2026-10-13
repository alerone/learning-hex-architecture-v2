package com.guildboard.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * AdventurerEntity
 */
@Entity 
@Table(name = "adventurers")
@AllArgsConstructor  
@Getter 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdventurerEntity {

    @Id 
    @Column(name = "id") 
    private String id;

    @Column(name = "name", nullable = false) 
    private String name;

    @Column(name = "adventurer_rank", nullable = false) 
    private String rank;
    
    @Column(name = "experience", nullable = false) 
    private int experience;
}
