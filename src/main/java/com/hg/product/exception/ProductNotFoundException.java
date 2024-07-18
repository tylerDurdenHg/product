package com.hg.product.exception;

public class ProductNotFoundException extends GenericException {

	public ProductNotFoundException(String code, String message) {
		super(code, message);
	}

}
