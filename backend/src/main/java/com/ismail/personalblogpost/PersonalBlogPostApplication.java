package com.ismail.personalblogpost;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication

public class PersonalBlogPostApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalBlogPostApplication.class, args);
	}

}
