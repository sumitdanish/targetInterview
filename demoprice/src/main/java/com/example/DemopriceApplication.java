package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackages = {"com.example"})
@PropertySource({"classpath:oauth2.properties"})
@SpringBootApplication
@EnableAutoConfiguration
public class DemopriceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemopriceApplication.class, args);
	}
}
