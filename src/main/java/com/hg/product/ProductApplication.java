package com.hg.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductApplication {

	public static void main(String[] args) {
		String formatted = "unusal text %s between %s".formatted("test1", "test2");
		System.out.println(formatted);
		SpringApplication.run(ProductApplication.class, args);
	}

}
