package no.ntnu.idatt2106;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Main class to run the application.
 */
@SpringBootApplication
@EnableCaching
public class Main {

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

}
