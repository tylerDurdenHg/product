package com.hg.product.entity;

import java.util.Objects;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.hg.product.enums.ProductType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Table
@Entity
@SQLRestriction("deleted=false") 
@SQLDelete(sql = "UPDATE product SET deleted = true, version=version+1 WHERE id=? and version=?")
public class Product extends BaseEntity {
	
	@NotNull
	@Column(name = "name", nullable = false)
	private String name;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private ProductType type;

	@Min(value = 1)
	@Column(name = "quantity", nullable = false)
	private int quantity;

	private boolean deleted = Boolean.FALSE;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.getId(),name, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		
		Product other = (Product) obj;
		return  Objects.equals(this.getId(), other.getId()) 
				&& Objects.equals(this.name, other.name) 
				&& this.type == other.type;
	}
	

}
