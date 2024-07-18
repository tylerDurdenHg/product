package com.hg.product.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse(
		@JsonProperty(value = "code") String code, 
		@JsonProperty(value = "errors") List<ErrorDTO> errors, 
		@JsonProperty(value = "results") Object results) {
	
	public ApiResponse(String code, List<ErrorDTO> errors, Object results) 
	{
		this.code = code;
		this.errors = List.copyOf(errors);
		this.results = results;
	}
}
