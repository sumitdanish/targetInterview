package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackages = {"com.example"})
@PropertySource({ "classpath:endpoint.properties","classpath:oauth2.properties" })
@SpringBootApplication
@EnableAutoConfiguration
public class DemoresourceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoresourceApplication.class, args);
	}
}