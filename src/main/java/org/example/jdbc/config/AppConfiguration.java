package org.example.jdbc.config;

import org.example.jdbc.util.ConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public ConnectionManager getConnectionManager(){
        return new ConnectionManager();
    }
}
