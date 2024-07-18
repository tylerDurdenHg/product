package com.hg.product.exception;

import jakarta.validation.constraints.NotBlank;

public class GenericException extends RuntimeException {

	private final String code;

	public GenericException(@NotBlank String code, @NotBlank String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
