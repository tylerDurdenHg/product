package com.hg.product.exception;

import jakarta.validation.constraints.NotBlank;

public class GenericException extends RuntimeException {

	private final String code;

	public GenericException(@NotBlank String code, @NotBlank String message, Throwable ex) {
		super(code + "->" + message, ex);
		this.code = code;
	}

	public GenericException(@NotBlank String code, @NotBlank String message) {
		super(code + "->" + message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
