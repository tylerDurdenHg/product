package com.hg.product.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;

public enum ProductType {

	HOME("home"), VEHICLE("vehicle"), OFFICE("office");
	
	private final String description;

	ProductType(String description) {
		this.description = description;
	}
	
	private String description() {
		return description;
	}
	
	public static Optional<ProductType> findType(String type) {
		try {
			return Optional.of(ProductType.valueOf(type.toUpperCase()));
		} catch (Exception e) {
			return Optional.empty();
		}
	}


}
