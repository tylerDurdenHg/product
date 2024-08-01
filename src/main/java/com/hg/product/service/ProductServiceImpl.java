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

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public Product saveProduct(ProductRequestDTO requestDTO) {
        try {
            Product product = ProductMapper.productRequestToProduct(requestDTO);
            return repo.save(product);
        } catch (Exception e) {
            if (e instanceof GenericException) {
                log.error("ProductServiceImpl::saveProduct Exception with dto:{}", requestDTO, e);
                throw e;
            }
            log.error("Non Business Exception with ", e);
            throw new BusinessInternalException("REPO-0001", String.format("Exception saving db for product:%s", requestDTO), e);
        }
    }

    @Transactional
    @Override
    public Product updateProduct(Long id, ProductRequestDTO requestDTO) {
        try {
            Product product = fetchById(id);
            Product productUpdated = ProductMapper.updateProductWithNewValues(product, requestDTO);
            return repo.save(productUpdated);
        } catch (Exception e) {
            if (e instanceof GenericException) {
                log.error("ProductServiceImpl::updateProduct Exception:{}", id, e);
                throw e;
            }
            log.error("Non Business Exception with ", e);
            throw new BusinessInternalException("REPO-UPDATE", String.format("Exception when updating with id:%s", id), e);
        }
    }

    @Override
    public boolean deleteProductById(Long id) {
        try {
            fetchById(id);
            repo.deleteById(id);
        } catch (Exception e) {
            if (e instanceof GenericException) {
                log.error("ProductServiceImpl::deleteProductById Exception product id:{}", id, e);
                throw e;
            }
            log.error("Non Business Exception with ", e);
            throw new BusinessInternalException("REPO-DELETE", String.format("Exception while deleting product with id:%s", id), e);
        }
        return true;
    }

    @Override
    public Product findById(Long id) {
        try {
            return fetchById(id);
        } catch (Exception e) {
            if (e instanceof GenericException) {
                log.error("ProductServiceImpl::findById Exception:", e);
                throw e;
            }
            log.error("Non Business Exception with ", e);
            throw new BusinessInternalException("REPO-FINDBYID", String.format("Exception while fetching product with id:%s", id), e);
        }
    }

    @Override
    public List<Product> findProductByType(String type) {
        List<Product> products;
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
            if (e instanceof GenericException) {
                log.error("ProductServiceImpl::findProductByType Exception product type:{}", type, e);
                throw e;
            }
            log.error("Non Business Exception with ", e);
            throw new BusinessInternalException("REPO-FINDTTYPE", String.format("Exception while fetching product with type:%s", type), e);
        }
        return products;
    }

    @Override
    public List<Product> findProductsToTypes() {
        List<Product> response;
        try {
            response = repo.findProductsToTypes();
            if (response == null || response.isEmpty()) {
                throw new ProductNotFoundException("PRD-NEXS",
                        "There is not any  product with any types");
            }

        } catch (Exception e) {
            if (e instanceof GenericException) {
                log.error("ProductServiceImpl::findProductsToTypes Exception fetching all types", e);
                throw e;
            }
            log.error("Non Business Exception with ", e);
            throw new BusinessInternalException("REPO-FINDTTYPES", "Exception while fetching products to all types", e);
        }
        return response;
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> products;
        try {
            products = repo.findAll();
            if (products.isEmpty()) {
                throw new ProductNotFoundException("PRD-NEXS", "There is not any product");
            }
        } catch (Exception e) {
            if (e instanceof GenericException) {
                log.error("ProductServiceImpl::findAllProducts Exception fetching all types", e);
                throw e;
            }
            log.error("Non Business Exception with ", e);
            throw new BusinessInternalException("REPO-FINDALL", "Exception while fetching all products", e);
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
