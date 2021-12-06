package com.example.wg_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class WgBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WgBackendApplication.class, args);
    }
}
