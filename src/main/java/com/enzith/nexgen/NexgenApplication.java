package com.enzith.nexgen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NexgenApplication {

	public static void main(String[] args) {
		SpringApplication.run(NexgenApplication.class, args);
	}

}
