package com.upt.generator.datc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DatcApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatcApplication.class, args);
    }

}

