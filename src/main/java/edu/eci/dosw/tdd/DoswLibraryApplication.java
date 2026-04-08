package edu.eci.dosw.tdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "edu.eci.dosw.tdd.persistence.relational.repository")
@EntityScan(basePackages = "edu.eci.dosw.tdd.persistence.relational.entity")
@EnableMongoRepositories(basePackages = "edu.eci.dosw.tdd.persistence.nonrelational.repository")

public class DoswLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoswLibraryApplication.class, args);
	}

}
