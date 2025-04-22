package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner initRoles(RoleRepository roleRepository) {
		return args -> {
			if(roleRepository.findByName("USER") == null) {
				roleRepository.save(new Role(null, "USER", new HashSet<>()));
			}
			if(roleRepository.findByName("ADMIN") == null) {
				roleRepository.save(new Role(null, "ADMIN", new HashSet<>()));
			}
		};
	}
}
