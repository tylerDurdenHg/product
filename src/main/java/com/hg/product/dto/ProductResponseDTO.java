package com.hg.product.dto;

import java.time.LocalDateTime;

public record ProductResponseDTO(
		String name, 
		String type, 
		int quantity, 
		LocalDateTime createdAt, 
		LocalDateTime updatedAt
		) 
{	
	public ProductResponseDTO (Builder builder) {
		this(builder.name, builder.type, builder.quantity, builder.createdAt, builder.updatedAt);
	}
	
	public static class Builder {
		private String name; 
		private String type; 
		private int quantity; 
		private LocalDateTime createdAt; 
		private LocalDateTime updatedAt;
		
		public Builder name(String name)
		{
			this.name = name;
			return this;
		}
		public Builder type(String type)
		{
			this.type = type;
			return this;
		}
		public Builder quantity(int quantity)
		{
			this.quantity = quantity;
			return this;
		}
		public Builder createdAt(LocalDateTime createdAt)
		{
			this.createdAt = createdAt;
			return this;
		}
		public Builder updatedAt(LocalDateTime updatedAt)
		{
			this.updatedAt = updatedAt;
			return this;
		}		
		
		public ProductResponseDTO build() 
		{
			return new ProductResponseDTO(this);
		}
	
	}
		
}
