package com.plume;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class PlumeBackendApplication implements CommandLineRunner {

	private final ConfigurableApplicationContext context;

	public PlumeBackendApplication(ConfigurableApplicationContext context) {this.context = context;}
	public static void main(String[] args) {
		SpringApplication.run(PlumeBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		String project = context.getEnvironment().getProperty("application.name");
		System.out.println(io.leego.banana.BananaUtils.bananaify(project));
	}
}
