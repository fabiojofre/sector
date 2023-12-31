package com.jofre;

import java.util.TimeZone;

import jakarta.annotation.PostConstruct;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SectorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SectorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}

	@PostConstruct
	public void init(){
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("America/Recife"));
	}
}
