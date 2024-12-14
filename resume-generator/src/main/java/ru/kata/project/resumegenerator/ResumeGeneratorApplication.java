package ru.kata.project.resumegenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.kata.project.resumegenerator", "infrastructure.controller", "application"})
@EnableMongoRepositories(basePackages = "infrastructure.repository")
public class ResumeGeneratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResumeGeneratorApplication.class, args);
    }
}
