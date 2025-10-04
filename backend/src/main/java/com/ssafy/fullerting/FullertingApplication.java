package com.ssafy.fullerting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
//@EntityScan(basePackages = "com.ssafy.fullerting")

@EnableScheduling
@EnableAsync
public class FullertingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullertingApplication.class, args);

	}

}
