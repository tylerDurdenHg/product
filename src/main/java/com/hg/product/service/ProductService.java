package com.hg.product.service;

import java.util.List;

import com.hg.product.dto.ProductRequestDTO;
import com.hg.product.entity.Product;

/**
	<li> first line in javadoc</li>
	<li> second line</li>
 	<img src="https://www.google.com/logos/doodles/2024/paris-games-surfing-6753651837110528-s.png" />
 **/

public interface ProductService {

	Product saveProduct(ProductRequestDTO requestDTO);
	
	Product updateProduct(Long id, ProductRequestDTO requestDTO);
	
	boolean deleteProductById(Long id);
	
	Product findById(Long id);

	List<Product> findAllProducts();

	List<Product> findProductByType(String type);

	List<Product> findProductsToTypes();

}
