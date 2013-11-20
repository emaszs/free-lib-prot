package com.psilonsoft.support;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DatabaseConfig {
    
    @Bean
    public DataSource postgresDataSource() throws Exception {
        return new DriverManagerDataSource("jdbc:postgresql://localhost:15432/nortallab", "nortallab", "nortallab");
    }
}
