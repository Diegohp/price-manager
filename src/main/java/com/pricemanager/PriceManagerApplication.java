package com.pricemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main SprintBoot application class
 * */
@SpringBootApplication(scanBasePackages = "com.pricemanager")
@EntityScan("com.pricemanager.infrastructure.adapter.out.persistence")
@EnableJpaRepositories("com.pricemanager.infrastructure.adapter.out.persistence")
public class PriceManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PriceManagerApplication.class, args);
    }
}