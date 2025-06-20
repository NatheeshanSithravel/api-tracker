package com.apisettracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ApiSetTrackerApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApiSetTrackerApplication.class)
                .properties("spring.config.name:application,application-wildfly");
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiSetTrackerApplication.class, args);
    }
}