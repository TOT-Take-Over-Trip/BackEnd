package com.trip.tot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.trip")
public class TotApplication {

	public static void main(String[] args) {
		SpringApplication.run(TotApplication.class, args);
	}

}
