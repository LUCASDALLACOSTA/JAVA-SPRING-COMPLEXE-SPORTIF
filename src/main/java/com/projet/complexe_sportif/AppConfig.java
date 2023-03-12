package com.projet.complexe_sportif;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy 
public class AppConfig {
    @Bean
    public TimeConverter timeConverter() {
        return new TimeConverter();
    }

}
