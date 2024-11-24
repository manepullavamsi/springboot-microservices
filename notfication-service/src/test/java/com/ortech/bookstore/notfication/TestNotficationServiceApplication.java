package com.ortech.bookstore.notfication;

import org.springframework.boot.SpringApplication;

public class TestNotficationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(NotficationServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
