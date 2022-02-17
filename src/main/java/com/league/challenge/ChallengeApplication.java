package com.league.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages={"com.league.challenge", "com.league.challenge.repository", "com.league.challenge.service"})
@RestController
@EnableAutoConfiguration
public class ChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

	@RequestMapping("/home")
	public String hello() {
		System.out.println("Hello buddy!!!");
		return "Hello buddy!";
	}

}
