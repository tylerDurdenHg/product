package com.hg.product.repository;

import com.hg.product.entity.Product;
import com.hg.product.enums.ProductType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.yml")
public class ProductRepositoryTest {

    @Autowired
    ProductRepository repository;

    Product product1;
    Product product2;

    @BeforeEach
    void beforeEach() {
        product1 = new Product();
        product1.setName("product");
        product1.setProductType(ProductType.VEHICLE);
        product1.setQuantity(4);

        product2 = new Product();
        product2.setName("other product");
        product2.setProductType(ProductType.HOME);
        product2.setQuantity(3);

        repository.save(product1);
        repository.save(product2);
    }

    @AfterEach
    void afterEach() {
        repository.deleteAll();
    }

    @Test
    void testFindAll() {
        List<Product> actual = repository.findAll();
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getId()).isGreaterThan(0L);
        assertThat(actual.get(1).getId()).isGreaterThan(0L);
        assertThat(actual.get(0).getName()).isEqualTo("product");
    }

    @Test
    void testCreateProduct() {
        product1.setId(300L);
        Product created = repository.save(product1);
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();
        assertThat(created.getId()).isGreaterThan(0L);
    }

    @Test
    void testDeleteProduct() {
        repository.delete(product1);

        assertThatThrownBy(() -> repository.findById(100L).get())
                .isExactlyInstanceOf(NoSuchElementException.class);
    }


}
