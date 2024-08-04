package com.hg.product.exception;

public class NonBusinessException extends GenericException {

	public NonBusinessException(String code, String message, Throwable ex) {
		super(code, message, ex);
	}
}
