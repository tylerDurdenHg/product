package com.hg.product.exception;

public class BusinessInternalException extends GenericException {

	public BusinessInternalException(String code, String message, Throwable ex) {
		super(code, message, ex);
	}
}
