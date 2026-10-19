package com.guildboard.tokenissuer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * TokenIssuerApplication
 */
@SpringBootApplication 
@ConfigurationPropertiesScan 
public class TokenIssuerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TokenIssuerApplication.class, args);
    }
}
