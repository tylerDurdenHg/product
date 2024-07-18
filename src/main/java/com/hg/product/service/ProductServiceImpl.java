package com.hg.product.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hg.product.dto.ProductRequestDTO;
import com.hg.product.entity.Product;
import com.hg.product.enums.ProductType;
import com.hg.product.exception.BusinessInternalException;
import com.hg.product.exception.GenericException;
import com.hg.product.exception.ProductNotFoundException;
import com.hg.product.mapper.ProductMapper;
import com.hg.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	private final ProductRepository repo;

	public ProductServiceImpl(ProductRepository repo) 
	{
		this.repo = repo;
	}

	@Override
	public Product saveProduct(ProductRequestDTO requestDTO) 
	{
		try {
			Product product = ProductMapper.productRequestToProduct(requestDTO);
			return repo.save(product);
		} catch (Exception e) {
			if (!(e instanceof GenericException)) {
				log.error("ProductServiceImpl::saveProduct Exception: {} {}", requestDTO,
						e.getMessage());
				throw new BusinessInternalException("Business Exception",
						String.format("Exception occurred while saving to db product:%s", requestDTO));
			}
			throw e;
		}
	}

	@Transactional
	@Override
	public Product updateProduct(Long id, ProductRequestDTO requestDTO) 
	{
		try {
			Product product = fetchById(id);
			Product productUpdated = ProductMapper.updateProductWithNewValues(product, requestDTO);
			return repo.save(productUpdated);
		} catch (Exception e) {
			if (!(e instanceof GenericException)) {
				log.error("ProductServiceImpl::updateProduct Exception:{}", id);
				throw new BusinessInternalException("Business Exception",
						String.format("Exception occured while updating product with id:%s", id));
			}
			throw e;
		}
	}

	@Override
	public boolean deleteProductById(Long id) 
	{
		try {
			fetchById(id);
			repo.deleteById(id);
		} catch (Exception e) {
			if (!(e instanceof GenericException)) {
				log.error("ProductServiceImpl::deleteProductById Exception: {}", id);
				throw new BusinessInternalException("Business Exception",
						String.format("Exception occured while deleting product with id:%s", id));
			}
			throw e;
		}
		return true;
	}

	@Override
	public Product findById(Long id) 
	{
		try {
			return fetchById(id);
		} catch (Exception e) {
			if (!(e instanceof GenericException)) {
				log.error("ProductServiceImpl::findById Exception: {} {}", id, e.getMessage());
				throw new BusinessInternalException("Business Exception",
						String.format("Exception occurred while fetching the product by id:%s", id));
			}
			throw e;
		}
	}

	@Override
	public List<Product> findProductByType(String type) 
	{
		List<Product> products;
		try {
			Optional<ProductType> productType = ProductType.findType(type);
			if (productType.isEmpty()) {
				throw new ProductNotFoundException("Type Not Exist",
						String.format("Product's type:%s not found", type));
			}

			products = repo.findProductByType(productType.get());
			if (products == null || products.isEmpty()) {
				throw new ProductNotFoundException("Product Not Exist",
						String.format("There is not any product to this type:%s", type));
			}
		} catch (Exception e) {

			if (!(e instanceof GenericException)) {
				log.error("ProductServiceImpl::findProductByType  Exception: {} {}", type,
						e.getMessage());
				throw new BusinessInternalException("Business Exception",
						String.format("Exception occurred while fetching the product by type:%s", type));
			}
			throw e;
		}
		return products;
	}

	@Override
	public List<Product> findProductsToTypes() 
	{
		List<Product> response;
		try {
			response = repo.findProductsToTypes();
			if (response == null || response.isEmpty()) {
				throw new ProductNotFoundException("Products Not Exists",
                        "There is not any  product with any types");
			}

		} catch (Exception e) {
			if (!(e instanceof GenericException)) {
				log.error("ProductServiceImpl::findProductsToTypes  Exception: {}",
						e.getMessage());
				throw new BusinessInternalException("Business Exception",
                        "Exception occurred while fetching the product to all types");
			}
			throw e;
		}
		return response;
	}

	@Override
	public List<Product> findAllProducts() 
	{
		List<Product> products;
		try {
			products = repo.findAll();
			if (products.isEmpty()) {
				throw new ProductNotFoundException("Product Not Exist", "There is not any product");
			}
		} catch (Exception e) {
			if (!(e instanceof GenericException)) {
				log.error("ProductServiceImpl::findAllProducts Exception: {}", e.getMessage());
				throw new BusinessInternalException("Business Exception",
						"Exception occurred while fetching all products");
			}
			throw e;
		}
		return products;
	}

	private Product fetchById(Long id) 
	{
		return repo
				.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product Not Exist",
						String.format("Product id:%s not found", id)));
	}

}
