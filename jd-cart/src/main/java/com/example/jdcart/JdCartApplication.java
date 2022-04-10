package com.example.jdcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication(scanBasePackages = {"com.example.jdcart.controller","com.example.config"})
public class JdCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdCartApplication.class, args);
    }

}
