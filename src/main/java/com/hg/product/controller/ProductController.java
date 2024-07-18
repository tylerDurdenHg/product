package com.hg.product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hg.product.dto.ApiResponse;
import com.hg.product.dto.ProductRequestDTO;
import com.hg.product.facede.ProductFacade;

import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(path = "v1/product")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductFacade facade;

    public ProductController(ProductFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveProduct(@Valid @RequestBody final ProductRequestDTO requestDTO) {
        log.info("ProductController::saveProduct request: {}", requestDTO);
        ApiResponse apiResponse = facade.saveProduct(requestDTO);
        log.info("ProductController::saveProduct response: {}", apiResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping(path = "/id/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@Valid @PathVariable(value = "id") Long id,
                                                     @Valid @RequestBody final ProductRequestDTO requestDTO) {
        log.info("ProductController::updateProduct request: {}", requestDTO);
        ApiResponse apiResponse = facade.updateProduct(id, requestDTO);
        log.info("ProductController::updateProduct response: {}", apiResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<ApiResponse> deleteProductById(@Valid @PathVariable(value = "id") final Long id) {
        log.info("ProductController::deleteProduct request: {}", id);
        ApiResponse apiResponse = facade.deleteProductById(id);
        log.info("ProductController::deleteProduct response: {}", apiResponse.results());
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<ApiResponse> getProductById(@Nonnull @PathVariable(value = "id") final Long id) {
        log.info("ProductController::getProductById request: {} ", id);
        ApiResponse apiResponse = facade.findById(id);
        log.info("ProductController::getProductById response: {}", apiResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


    @GetMapping(path = "/type/{type}")
    public ResponseEntity<ApiResponse> getProductByType(@NotBlank @PathVariable(value = "type") final String type) {
        log.info("ProductController::getProductByType request: {}", type);
        ApiResponse apiResponse = facade.findProductByType(type);
        log.info("ProductController::getProductByType response: {}", apiResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


    @GetMapping(path = "/types")
    public ResponseEntity<ApiResponse> getProductsToTypes() {
        ApiResponse apiResponse = facade.findProductsToTypes();
        log.info("ProductController::getProductsToTypes response: {}", apiResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts() {
        ApiResponse apiResponse = facade.findAllProducts();
        log.info("ProductController::getAllProducts response: {}", apiResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
