package com.backend.swp.apalary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApalaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApalaryApplication.class, args);
	}

}
