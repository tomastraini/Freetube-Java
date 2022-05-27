package com.freetube.JavaFreetube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class JavaFreetubeApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(JavaFreetubeApplication.class, args);
	}

}
