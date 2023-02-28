package uz.dam.userservice.config;

import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class ScalarGraphqlTypesConfig {
    @Bean
    public RuntimeWiringConfigurer dateTime() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.DateTime);
    }
    @Bean
    public RuntimeWiringConfigurer date() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.Date);
    }
}