package com.ismail.personalblogpost.config;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlyWayConfig {
    @Bean()
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return (strategy) -> {
            strategy.clean();
            strategy.migrate();
        };
    }
}
