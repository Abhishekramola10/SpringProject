package com.BootCampProject1.BootCampProject1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.BootCampProject1")
@EnableJpaRepositories(basePackages = "com.BootCampProject1") //repo package
@EntityScan(basePackages = "com.BootCampProject1")	//entity package
public class BootCampProject1Application {

	public static void main(String[] args) {
		SpringApplication.run(BootCampProject1Application.class, args);
	}


}
