package com.instahyre.caller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CallerInstahyreApplication {
	public static void main(String[] args) {
		SpringApplication.run(CallerInstahyreApplication.class, args);
	}

}
