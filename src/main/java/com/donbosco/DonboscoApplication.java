package com.donbosco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.donbosco")
public class DonboscoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DonboscoApplication.class, args);
	}

}
