package com.example.fitnesstracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FitnesstrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FitnesstrackerApplication.class, args);
    }

}
