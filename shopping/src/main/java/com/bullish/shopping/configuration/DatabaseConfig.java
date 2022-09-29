/*
package com.bullish.shopping.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("{server.profiles.active}")
    private String PROFILE;

    @Bean
    public DataSource dataSource() {

        switch (PROFILE) {
            case "local" :
                return getH2DataSource();

                default : return getH2DataSource();
        }
    }

    private DataSource getH2DataSource() {

        //Creating an in-memory database
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
        dataSource.setSchema("bullish");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");

        return dataSource;
    }
}
*/
