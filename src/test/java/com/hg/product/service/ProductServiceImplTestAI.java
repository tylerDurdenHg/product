package com.hg.product.service;

import com.hg.product.dto.ProductRequestDTO;
import com.hg.product.entity.Product;
import com.hg.product.enums.ProductType;
import com.hg.product.mapper.ProductMapper;
import com.hg.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTestAI {

    @Mock
    private ProductRepository repo;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveProduct() {
        ProductRequestDTO requestDTO = new ProductRequestDTO(null, "product", "vehicle", 1);
        Product product = new Product();

        try (MockedStatic<ProductMapper> mockedStatic = Mockito.mockStatic(ProductMapper.class)) {
            mockedStatic.when(() -> ProductMapper.productRequestToProduct(requestDTO)).thenReturn(product);
            when(repo.save(product)).thenReturn(product);

            Product savedProduct = productServiceImpl.saveProduct(requestDTO);

            assertNotNull(savedProduct);
            verify(repo, times(1)).save(product);
        }
    }

    @Test
    void testUpdateProduct() {
        Long productId = 1L;
        ProductRequestDTO requestDTO = new ProductRequestDTO(null, "product", "vehicle", 2);
        Product existingProduct = new Product();
        Product updatedProduct = new Product();

        when(repo.findById(productId)).thenReturn(Optional.of(existingProduct));

        try (MockedStatic<ProductMapper> mockedMapper = mockStatic(ProductMapper.class)) {
            mockedMapper.when(() -> ProductMapper.updateProductWithNewValues(existingProduct, requestDTO)).thenReturn(updatedProduct);
            when(repo.save(updatedProduct)).thenReturn(updatedProduct);

            Product result = productServiceImpl.updateProduct(productId, requestDTO);

            assertNotNull(result);
            verify(repo, times(1)).findById(productId);
            verify(repo, times(1)).save(updatedProduct);
        }
    }

    @Test
    void testDeleteProductById() {
        Long productId = 1L;
        Product existingProduct = new Product();

        when(repo.findById(productId)).thenReturn(Optional.of(existingProduct));

        boolean result = productServiceImpl.deleteProductById(productId);

        assertTrue(result);
        verify(repo, times(1)).deleteById(productId);
    }

    @Test
    void testFindById() {
        Long productId = 1L;
        Product product = new Product();

        when(repo.findById(productId)).thenReturn(Optional.of(product));

        Product result = productServiceImpl.findById(productId);

        assertNotNull(result);
        verify(repo, times(1)).findById(productId);
    }

    @Test
    void testFindProductByType() {
        String type = "ELECTRONICS";
        ProductType productType = ProductType.VEHICLE;
        Product product = new Product();
        try (MockedStatic<ProductType> mockedStatic = Mockito.mockStatic(ProductType.class)) {
            mockedStatic.when(() -> ProductType.findType(type)).thenReturn(Optional.of(productType));
            when(repo.findProductByType(productType)).thenReturn(List.of(product));

            List<Product> products = productServiceImpl.findProductByType(type);

            assertNotNull(products);
            assertFalse(products.isEmpty());
            verify(repo, times(1)).findProductByType(productType);
        }
    }

    @Test
    void testFindProductsToTypes() {
        Product product = new Product();

        when(repo.findProductsToTypes()).thenReturn(List.of(product));

        List<Product> products = productServiceImpl.findProductsToTypes();

        assertNotNull(products);
        assertFalse(products.isEmpty());
        verify(repo, times(1)).findProductsToTypes();
    }

    @Test
    void testFindAllProducts() {
        Product product = new Product();

        when(repo.findAll()).thenReturn(List.of(product));

        List<Product> products = productServiceImpl.findAllProducts();

        assertNotNull(products);
        assertFalse(products.isEmpty());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testFetchById() {
        Long productId = 1L;
        Product product = new Product();

        when(repo.findById(productId)).thenReturn(Optional.of(product));

        Product result = productServiceImpl.findById(productId);

        assertNotNull(result);
        verify(repo, times(1)).findById(productId);
    }
}
