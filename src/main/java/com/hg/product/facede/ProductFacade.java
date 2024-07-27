package com.hg.product.facede;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.hg.product.dto.SimpleResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hg.product.dto.ApiResponse;
import com.hg.product.dto.ProductRequestDTO;
import com.hg.product.dto.ProductResponseDTO;
import com.hg.product.entity.Product;
import com.hg.product.enums.ProductType;
import com.hg.product.mapper.ProductMapper;
import com.hg.product.service.ProductService;

@Service
public class ProductFacade {

    private final ProductService service;

    public ProductFacade(ProductService service) {
        this.service = service;
    }

    public ApiResponse saveProduct(final ProductRequestDTO requestDTO) {
        ProductResponseDTO responseDTO = ProductMapper.productToProductResponseDTO(service.saveProduct(requestDTO));
        return new ApiResponse(HttpStatus.OK.name(), List.of(), responseDTO);
    }

    public ApiResponse updateProduct(final Long id, final ProductRequestDTO requestDTO) {
        ProductResponseDTO responseDTO = ProductMapper.productToProductResponseDTO(service.updateProduct(id, requestDTO));
        return new ApiResponse(HttpStatus.OK.name(), List.of(), responseDTO);
    }

    public ApiResponse deleteProductById(final Long id) {
        boolean isDeleted = service.deleteProductById(id);
        String deletedMessage = String.format("Id:%s deleted:%s", id, isDeleted);
        return new ApiResponse(HttpStatus.OK.name(), List.of(), new SimpleResponseDTO(deletedMessage));
    }

    public ApiResponse findById(final Long id) {
        ProductResponseDTO responseDTO = ProductMapper.productToProductResponseDTO(service.findById(id));
        return new ApiResponse(HttpStatus.OK.name(), List.of(), responseDTO);
    }

    public ApiResponse findProductByType(final String type) {
        List<ProductResponseDTO> responseDTOs = service.findProductByType(type)
                .stream()
                .map(ProductMapper::productToProductResponseDTO)
                .toList();
        return new ApiResponse(HttpStatus.OK.name(), List.of(), responseDTOs);
    }

    public ApiResponse findProductsToTypes() {
        Map<ProductType, List<Product>> allProductsToTypes = service.findProductsToTypes()
                .stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(Product::getProductType, Collectors.toList()),
                        Collections::unmodifiableMap
                ));

        return new ApiResponse(HttpStatus.OK.name(), List.of(), allProductsToTypes);
    }

    public ApiResponse findAllProducts() {
        List<ProductResponseDTO> allProducts = service.findAllProducts()
                .stream()
                .map(ProductMapper::productToProductResponseDTO)
                .toList();
        return new ApiResponse(HttpStatus.OK.name(), List.of(), allProducts);
    }

}

