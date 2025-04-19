package no.ntnu.idatt2106.config;

import no.ntnu.idatt2106.utils.TransactionCategorisation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Configuration class for the TransactionCategorisation class.
 * Responsible for creating a bean of the TransactionCategorisation class.
 */
@Configuration
public class CategorisationConfig {

    @Bean
    public TransactionCategorisation transactionCategorisation() throws IOException {
        return new TransactionCategorisation();
    }
}