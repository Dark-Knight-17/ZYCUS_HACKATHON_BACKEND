package com.backend.zycus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // <--- THIS IS REQUIRED
public class ZycusApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZycusApplication.class, args);
	}

}
