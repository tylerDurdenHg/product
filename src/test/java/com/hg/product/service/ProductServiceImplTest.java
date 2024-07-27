package com.hg.product.service;

import com.hg.product.dto.ProductRequestDTO;
import com.hg.product.entity.Product;
import com.hg.product.enums.ProductType;
import com.hg.product.exception.BusinessInternalException;
import com.hg.product.exception.ProductNotFoundException;
import com.hg.product.mapper.ProductMapper;
import com.hg.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceImplTest {

    @Mock
    private ProductRepository repo;

    @InjectMocks
    private ProductServiceImpl service;

    private ProductRequestDTO requestDTO;

    private Product product;


    @BeforeEach
    void beforeEach() {
        service = new ProductServiceImpl(repo);
        requestDTO = new ProductRequestDTO(null, "product-2", "VEHICLE", 1);
        product = ProductMapper.productRequestToProduct(requestDTO);
        product.setId(7L);
    }

    @Test
    void saveProduct() {
        service.saveProduct(requestDTO);
        verify(repo, times(1)).save(any(Product.class));

        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(repo).save(argumentCaptor.capture());
        Product insertedProduct = argumentCaptor.getValue();

        assertThat(insertedProduct.getName()).isEqualTo("product-2");
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProductById() {
        long id = 7L;
        when(repo.findById(id)).thenReturn(Optional.of(product));
        service.deleteProductById(id);
        verify(repo, times(1)).deleteById(id);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(repo).deleteById(argumentCaptor.capture());
        Long actualId = argumentCaptor.getValue();

        assertThat(actualId).isEqualTo(id);
    }

    @Test
    void findById() {
        long id = 7L;
        when(repo.findById(id)).thenReturn(Optional.of(product));

        Product expected = service.findById(id);
        verify(repo, times(1)).findById(id);

        assertThat(expected).isEqualTo(product);
    }

    @Test
    void findByInvalidId() {
        long id = 8L;
        when(repo.findById(id)).thenThrow(IllegalArgumentException.class);
        assertThatThrownBy(() -> service.findById(id))
                .hasMessageContaining("while fetching the product")
                .isExactlyInstanceOf(BusinessInternalException.class);

    }

    @Test
    void findProductByType() {
        String productType = ProductType.VEHICLE.name();

        List<Product> expected = List.of(product);
        when(repo.findProductByType(ProductType.valueOf(productType))).thenReturn(expected);
        List<Product> actual = service.findProductByType(productType);

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void findNotProductFoundWithType() {
        String productType = ProductType.OFFICE.name();

        when(repo.findProductByType(ProductType.valueOf(productType))).thenReturn(null);

        assertThatThrownBy(() -> service.findProductByType(productType))
                .hasMessageContaining("There is not any product to this type")
                .isExactlyInstanceOf(ProductNotFoundException.class);

    }

    @Test
    void findProductsToTypes() {
    }

    @Test
    void findAllProducts() {
        List<Product> expected = List.of(product);
        when(repo.findAll()).thenReturn(expected);

        List<Product> actual = service.findAllProducts();

        assertThat(expected).isEqualTo(actual);
    }
}
