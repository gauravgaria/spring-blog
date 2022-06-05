package com.blog.blogrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class BlogRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogRestApiApplication.class, args);
	}

}
