package com.iroff.supportlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SupportLabBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupportLabBackendApplication.class, args);
	}

}
