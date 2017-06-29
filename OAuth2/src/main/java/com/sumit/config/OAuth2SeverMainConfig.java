package com.sumit.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.sumit")
public class OAuth2SeverMainConfig {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2SeverMainConfig.class, args);
	}
}
