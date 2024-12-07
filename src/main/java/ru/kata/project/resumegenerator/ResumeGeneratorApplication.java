package ru.kata.project.resumegenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication @EnableMongoRepositories(basePackages = "infrastructure.controller")
public class ResumeGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResumeGeneratorApplication.class, args);
	}
}
