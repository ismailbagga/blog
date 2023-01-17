package com.ismail.personalblogpost;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

import java.util.regex.Pattern;

@SpringBootApplication()
public class PersonalBlogPostApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalBlogPostApplication.class, args);
//		final var pattern = Pattern.compile("(?=^[\\w-]+$)(?=^.*[a-zA-z-_].*$)") ;
//		String path = "data-structure" ;
//
//		System.out.println(pattern.matcher(path).find()) ;
	}

}
