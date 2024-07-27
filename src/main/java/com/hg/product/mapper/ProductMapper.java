package com.hg.product.mapper;

import java.time.LocalDateTime;
import java.util.Optional;

import com.hg.product.dto.ProductRequestDTO;
import com.hg.product.dto.ProductResponseDTO;
import com.hg.product.entity.Product;
import com.hg.product.enums.ProductType;
import com.hg.product.exception.ProductNotFoundException;

public class ProductMapper {

    public static ProductResponseDTO productToProductResponseDTO(final Product product) {
        return new ProductResponseDTO.Builder()
                .name(product.getName())
                .type(product.getProductType().name())
                .quantity(product.getQuantity())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public static Product productRequestToProduct(ProductRequestDTO dto) {
        Optional<ProductType> productType = getProductType(dto);

        Product product = new Product();
        product.setName(dto.name());
        product.setProductType(productType.get());
        product.setQuantity(dto.quantity());

        return product;
    }

    public static Product updateProductWithNewValues(Product product, ProductRequestDTO dto) {
        Optional<ProductType> productType = getProductType(dto);

        product.setName(dto.name());
        product.setProductType(productType.get());
        product.setQuantity(dto.quantity());
        product.setUpdatedAt(LocalDateTime.now());

        return product;
    }

    private static Optional<ProductType> getProductType(ProductRequestDTO dto) {
        Optional<ProductType> productType = ProductType.findType(dto.type());
        if (productType.isEmpty()) {
            throw new ProductNotFoundException("Type Not Exist",
                    String.format("Product's type:%S not found", dto.type()));
        }
        return productType;
    }

}
