package com.ismail.personalblogpost;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.regex.Pattern;

@SpringBootApplication

public class PersonalBlogPostApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalBlogPostApplication.class, args);
//		final Pattern WHITESPACE = Pattern.compile("\\s+") ;
//		final Pattern NOT_NORMAL_CHAR = Pattern.compile("[^\\w-]+") ;
//		String title = "  Django   ùis Awesome**$%  " ;
//		var noWhiteSpace = WHITESPACE.matcher(title.toLowerCase().strip()).replaceAll("-");
//		var no_latin = NOT_NORMAL_CHAR.matcher(noWhiteSpace).replaceAll("") ;
//		System.out.println(noWhiteSpace);
//		System.out.println(no_latin);

	}

}
