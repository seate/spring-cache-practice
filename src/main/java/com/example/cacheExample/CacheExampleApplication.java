package com.example.cacheExample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CacheExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CacheExampleApplication.class, args);
	}

}
