package com.hg.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hg.product.dto.ApiResponse;
import com.hg.product.dto.ProductRequestDTO;
import com.hg.product.dto.ProductResponseDTO;
import com.hg.product.facede.ProductFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductFacade facade;

    ProductRequestDTO requestDTO;

    ObjectMapper mapper = new ObjectMapper();
    ApiResponse apiResponse;
    List<ProductResponseDTO> responseDTOs;

    @BeforeEach
    void beforeEach() {
        requestDTO = new ProductRequestDTO(null, "product-1", "vehicle", 1);
        ProductResponseDTO dto = new ProductResponseDTO.Builder()
                .name("product-1")
                .type("vehicle")
                .quantity(2)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        responseDTOs = List.of(dto);
    }

    @Test
    void saveProduct() throws Exception {
        apiResponse = new ApiResponse("Created", List.of(), responseDTOs);
        Mockito.when(facade.saveProduct(requestDTO)).thenReturn(apiResponse);
        mockMvc.perform(
                        post("/v1/products")
                                .content(mapper.writeValueAsString(requestDTO))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is("Created")))
                .andExpect(jsonPath("$.errors", empty()))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data[0].name", is("product-1")));


    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProductById() {
    }

    @Test
    void getProductById() {
    }

    @Test
    void getProductByType() {
    }

    @Test
    void getProductsToTypes() {
    }

    @Test
    void getAllProducts() {
    }
}