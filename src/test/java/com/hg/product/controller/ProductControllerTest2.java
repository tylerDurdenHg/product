package com.hg.product.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hg.product.dto.ApiResponse;
import com.hg.product.dto.ProductRequestDTO;
import com.hg.product.dto.ProductResponseDTO;
import com.hg.product.facede.ProductFacade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@WebMvcTest(ProductController.class)
class ProductControllerTest2 {

    @Autowired
     MockMvc mockMvc;

    @MockBean
     ProductFacade facade;

    @Autowired
     ObjectMapper objectMapper;

     ProductRequestDTO requestDTO;
     List<ProductResponseDTO> responseDTOs;

    @BeforeEach
    void setUp() {

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
    void testSaveProduct() throws Exception {
        ApiResponse apiResponse = new ApiResponse("Created", Collections.emptyList(), responseDTOs);
        when(facade.saveProduct(any(ProductRequestDTO.class))).thenReturn(apiResponse);

        mockMvc.perform(post("/v1/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Long id = 1L;

        ApiResponse apiResponse = new ApiResponse("Created", Collections.emptyList(), responseDTOs);
        when(facade.updateProduct(eq(id), any(ProductRequestDTO.class))).thenReturn(apiResponse);

        mockMvc.perform(put("/v1/products/id/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    void testDeleteProductById() throws Exception {
        Long id = 1L;

        ApiResponse apiResponse = new ApiResponse("Created", Collections.emptyList(), responseDTOs);
        when(facade.deleteProductById(eq(id))).thenReturn(apiResponse);

        mockMvc.perform(delete("/v1/products/id/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    void testGetProductById() throws Exception {
        Long id = 1L;

        ApiResponse apiResponse = new ApiResponse("Created", Collections.emptyList(), responseDTOs);
        when(facade.findById(eq(id))).thenReturn(apiResponse);

        mockMvc.perform(get("/v1/products/id/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    void testGetProductByType() throws Exception {
        String type = "ELECTRONICS";

        ApiResponse apiResponse = new ApiResponse("Created", Collections.emptyList(), responseDTOs);
        when(facade.findProductByType(eq(type))).thenReturn(apiResponse);

        mockMvc.perform(get("/v1/products/type/{type}", type))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    void testGetProductsToTypes() throws Exception {

        ApiResponse apiResponse = new ApiResponse("Created", Collections.emptyList(), responseDTOs);
        when(facade.findProductsToTypes()).thenReturn(apiResponse);

        mockMvc.perform(get("/v1/products/types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    void testGetAllProducts() throws Exception {

        ApiResponse apiResponse = new ApiResponse("Created", Collections.emptyList(), responseDTOs);
        when(facade.findAllProducts()).thenReturn(apiResponse);

        mockMvc.perform(get("/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Success"));
    }
}
