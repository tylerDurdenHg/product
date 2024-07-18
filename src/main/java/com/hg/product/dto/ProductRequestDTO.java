package com.hg.product.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ProductRequestDTO(
		Long id,
		@NotBlank(message = "product name cannot be blank") String name,
		@NotBlank(message = "product type cannot be blank") String type,
		@Min(value = 1, message = "product count must be bigger than 1") int quantity
		) {

}
