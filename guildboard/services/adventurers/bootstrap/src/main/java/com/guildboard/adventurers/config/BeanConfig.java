package com.guildboard.adventurers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.guildboard.adventurers.application.port.in.GetAdventurerUseCase;
import com.guildboard.adventurers.application.port.in.GrantExperienceUseCase;
import com.guildboard.adventurers.application.port.in.RegisterAdventurerUseCase;
import com.guildboard.adventurers.application.port.out.AdventurerRepository;
import com.guildboard.adventurers.application.service.GetAdventurerService;
import com.guildboard.adventurers.application.service.GrantExperienceService;
import com.guildboard.adventurers.application.service.RegisterAdventurerService;


/**
 * BeanConfig
 */
@Configuration
public class BeanConfig {

    @Bean 
    public RegisterAdventurerUseCase registerAdventurerUseCase(AdventurerRepository repo){
            return new RegisterAdventurerService(repo);
            }

    @Bean 
    public GetAdventurerUseCase getAdventurerUseCase(AdventurerRepository repo){
            return new GetAdventurerService(repo);
            }

    @Bean 
    public GrantExperienceUseCase grantExperienceUseCase(AdventurerRepository repo){
            return new GrantExperienceService(repo);
            }
}
