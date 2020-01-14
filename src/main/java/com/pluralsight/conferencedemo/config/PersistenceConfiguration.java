package com.pluralsight.conferencedemo.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfiguration {

    // Why work in config classes instead of just doing
    // something in properties files?
    // Do this when you need to do something complex
    // then just define it in the method and let
    // Spring do the work
    @Bean
    public DataSource dataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.url("jdbc:postgresql://localhost:5432/conference_app");
        System.out.println("Custom datasource initialized and set.");
        return builder.build();
    }
}
