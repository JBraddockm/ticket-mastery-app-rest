package org.example.config;

import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class AppConfig {
    @Bean
    Faker faker() {
        return new Faker(new Locale("en", "GB"));
    }
}
