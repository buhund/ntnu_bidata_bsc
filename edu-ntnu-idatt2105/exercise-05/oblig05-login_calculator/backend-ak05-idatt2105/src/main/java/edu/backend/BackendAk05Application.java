package edu.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"edu.backend", "edu.backend.utility"})

public class BackendAk05Application {

	public static void main(String[] args) {
		SpringApplication.run(BackendAk05Application.class, args);
	}

}
