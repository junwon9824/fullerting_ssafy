package com.ssafy.fullerting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
@EnableScheduling
public class FullertingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullertingApplication.class, args);
	}

}
