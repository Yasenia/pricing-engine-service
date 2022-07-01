package io.github.yasenia.testcore;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import javax.sql.DataSource;

public class DatabaseTestConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public PostgreSQLContainer<?> postgresqlContainer() {
        try (var sqlContainer = new PostgreSQLContainer<>("postgres:14-alpine")) {
            return sqlContainer.waitingFor(Wait.forListeningPort());
        }
    }

    @Bean
    @FlywayDataSource
    public DataSource dataSource(PostgreSQLContainer<?> postgresqlContainer) {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(postgresqlContainer.getJdbcUrl());
        hikariConfig.setUsername(postgresqlContainer.getUsername());
        hikariConfig.setPassword(postgresqlContainer.getPassword());
        return new HikariDataSource(hikariConfig);
    }
}
