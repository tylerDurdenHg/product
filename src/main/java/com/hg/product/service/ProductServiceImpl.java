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
import com.hg.product.exception.ProductNotFoundException;
import com.hg.product.mapper.ProductMapper;
import com.hg.product.repository.ProductRepository;

import static com.hg.product.logger.GenericLogger.handleException;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public Product saveProduct(ProductRequestDTO requestDTO) {
        Product result = null;
        try {
            Product product = ProductMapper.productRequestToProduct(requestDTO);
            result = repo.save(product);
        } catch (Exception e) {
            handleException(log, "ProductServiceImpl::saveProduct ", "when saving dto:", requestDTO, "REPO-0001", e);
        }
        return result;
    }

    @Transactional
    @Override
    public Product updateProduct(Long id, ProductRequestDTO requestDTO) {
        Product result = null;
        try {
            Product product = fetchById(id);
            Product productUpdated = ProductMapper.updateProductWithNewValues(product, requestDTO);
            result = repo.save(productUpdated);
        } catch (Exception e) {
            handleException(log, "ProductServiceImpl::updateProduct ", "when updating by id:", id, "REPO-0001", e);
        }
        return result;
    }

    @Override
    public boolean deleteProductById(Long id) {
        try {
            fetchById(id);
            repo.deleteById(id);
        } catch (Exception e) {
            handleException(log, "ProductServiceImpl::deleteProductById ", "when deleting product by id:", id, "REPO-0001", e);
        }
        return true;
    }

    @Override
    public Product findById(Long id) {
        Product result = null;
        try {
            result = fetchById(id);
        } catch (Exception e) {
            handleException(log, "ProductServiceImpl::findById ", "when fetching product by id:", id, "REPO-0001", e);
        }
        return result;
    }

    @Override
    public List<Product> findProductByType(String type) {
        List<Product> products = List.of();
        try {
            Optional<ProductType> productType = ProductType.findType(type);
            if (productType.isEmpty()) {
                throw new ProductNotFoundException("PRD-NEXS",
                        String.format("Product's type:%s not found", type));
            }

            products = repo.findProductByType(productType.get());
            if (products == null || products.isEmpty()) {
                throw new ProductNotFoundException("PRD-NEXS",
                        String.format("There is not any product to this type:%s", type));
            }
        } catch (Exception e) {
            handleException(log, "ProductServiceImpl::findProductByType", "when fetching product by type", type, "REPO-0001", e);
        }
        return products;
    }

    @Override
    public List<Product> findProductsToTypes() {
        List<Product> response = List.of();
        try {
            response = repo.findProductsToTypes();
            if (response == null || response.isEmpty()) {
                throw new ProductNotFoundException("PRD-NEXS", "There is not any  product with any types");
            }

        } catch (Exception e) {
            handleException(log, "ProductServiceImpl::findProductsToTypes", "when fetching product all types", null, "REPO-0001", e);
        }
        return response;
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> products = List.of();
        try {
            products = repo.findAll();
            if (products.isEmpty()) {
                throw new ProductNotFoundException("PRD-NEXS", "There is not any product");
            }
        } catch (Exception e) {
            handleException(log, "ProductServiceImpl::findAllProducts", "when fetching product all ", null, "REPO-0001", e);
        }
        return products;
    }

    private Product fetchById(Long id) {
        return repo
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("PRD-0001",
                        String.format("Product id:%s not found", id)));
    }

}
