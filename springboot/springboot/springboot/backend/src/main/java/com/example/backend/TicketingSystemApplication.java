package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.backend.repository")
public class TicketingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingSystemApplication.class, args);
    }
}
