package com.hg.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hg.product.entity.Product;
import com.hg.product.enums.ProductType;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(value = "select u from Product u where u.type = ?1")
	List<Product> findProductByType(ProductType type);

	
	@Query(value = "select * from Product u where u.type is not null", nativeQuery = true)
	List<Product> findProductsToTypes();

}
