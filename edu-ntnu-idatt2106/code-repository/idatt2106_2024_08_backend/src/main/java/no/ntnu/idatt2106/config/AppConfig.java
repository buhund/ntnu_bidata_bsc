package no.ntnu.idatt2106.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * AppConfig is a configuration class for the application.
 * Contains a Bean for password encoding.
 * The password encoding is used for encoding passwords before storing them in the database.
 *
 */
@Configuration
public class AppConfig {
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}