package com.hg.product.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hg.product.dto.ApiResponse;
import com.hg.product.dto.ProductResponseDTO;
import com.hg.product.repository.ProductRepository;
import com.hg.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    HttpHeaders httpHeader;

    ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void beforeEach() {
        httpHeader = new HttpHeaders();
        httpHeader.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Sql(statements = "insert into product(id, name, product_Type, quantity, deleted, version) values (100, 'product', 'VEHICLE', 1, false, 0)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from product where id=100", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllProducts() {

        HttpEntity<String> entity = new HttpEntity<>(null, httpHeader);

        // check for json
        ApiResponse apiResponse = restTemplate.exchange(serverUrl(), HttpMethod.GET,
                entity, new ParameterizedTypeReference<ApiResponse>() {
                }).getBody();
        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.code()).isEqualTo(HttpStatus.OK.name());
        assertThat(apiResponse.errors()).hasSize(0);
        assertThat(apiResponse.data()).isNotNull();

        // check for responseDTO
        List<ProductResponseDTO> responseDTOList = objectMapper.convertValue(apiResponse.data(),
                new TypeReference<>() {
                });
        ProductResponseDTO actual = responseDTOList.get(0);
        assertThat(actual).isNotNull();

        // check for service
        assertThat(actual.name()).isEqualTo(productService.findAllProducts().get(0).getName());

        //check for repo
        assertThat(actual.type()).isEqualTo(productRepository.findAll().get(0).getProductType().name());
    }

    private String serverUrl() {
        return "http://localhost:" + port + "/v1/products";
    }


}
