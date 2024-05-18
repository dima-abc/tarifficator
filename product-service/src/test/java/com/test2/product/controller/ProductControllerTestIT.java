package com.test2.product.controller;

import com.test2.product.enums.TypeProduct;
import com.test2.product.payload.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTestIT {
    private static final String PRODUCT_URI = "/api/v1/products/{uuid}";
    ProductDTO productDTO1 = ProductDTO.of()
            .id("eef26128-a527-4ae9-b08b-ef2207b08261").name("Product name1")
            .typeProductId(1).typeProductName(TypeProduct.LOAN.getName())
            .startDate("2022-12-01").endDate("2024-11-01").description("Product1 detail")
            .tariffId("ab008c85-e2e4-4bf7-894f-c60b99926ae2").tariffVersion(1L)
            .authorId("52f54cee-e2fd-4e49-8b75-f00cb690032c").version(0)
            .build();
    ProductDTO productDTO2 = ProductDTO.of()
            .id("b5711e0a-5aed-4df8-bbd2-5625e05a9799").name("Product name2")
            .typeProductId(2).typeProductName(TypeProduct.CARD.getName())
            .startDate("2020-10-11").endDate("2025-11-01").description("Product2 detail")
            .tariffId("13e1895d-2f1d-4766-a2a4-9af3adadfb38").tariffVersion(1L)
            .authorId("52f54cee-e2fd-4e49-8b75-f00cb690032c").version(1)
            .build();
    ProductDTO productDTO3 = ProductDTO.of()
            .id("761d98d6-ff53-4642-8727-15637347af37").name("Product name3")
            .typeProductId(1).typeProductName(TypeProduct.LOAN.getName())
            .startDate("2021-02-21").endDate("2026-11-01").description("Product3 detail")
            .tariffId("0aab4e94-5564-4ce4-8581-e6d956016844").tariffVersion(1L)
            .authorId("52f54cee-e2fd-4e49-8b75-f00cb690032c").version(2)
            .build();
    @Autowired
    MockMvc mockMvc;

    @Test
    void dependencyNotNull() {
        assertNotNull(this.mockMvc);
    }

    @Test
    @Sql("/sql/product_tariff_insert.sql")
    void findProduct_ProductExists_ReturnProduct() throws Exception {
        this.mockMvc.perform(get(PRODUCT_URI, productDTO1.getId())
                        .with(jwt().jwt(builder -> builder.claim("scope", "product_service"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                        "id":"eef26128-a527-4ae9-b08b-ef2207b08261",
                        "name":"Product name1",
                        "typeProductId":1,
                        "typeProductName":"Кредит",
                        "startDate":"2022-12-01",
                        "endDate":"2024-11-01",
                        "description":"Product1 detail",
                        "tariffId":"ab008c85-e2e4-4bf7-894f-c60b99926ae2",
                        "tariffVersion":1,
                        "authorId":"52f54cee-e2fd-4e49-8b75-f00cb690032c",
                        "version":0
                        }
                        """));
    }

    @Test
    void findProduct_ProductNotExists_ReturnNotFound() throws Exception {
        this.mockMvc.perform(get(PRODUCT_URI, UUID.randomUUID())
                        .with(jwt().jwt(builder -> builder.claim("scope", "product_service"))))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql("/sql/product_tariff_insert.sql")
    void updateProduct_RequestIsValid_ReturnNoContent() throws Exception {
        this.mockMvc.perform(patch(PRODUCT_URI, productDTO2.getId())
                        .with(jwt().jwt(builder -> builder.claim("scope", "product_service")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name":"New name product",
                                "typeProductId":1
                                }
                                """))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @Sql("/sql/product_tariff_insert.sql")
    void updateProduct_RequestIsInvalid_ReturnBadRequest() throws Exception {
        this.mockMvc.perform(patch(PRODUCT_URI, productDTO2.getId())
                        .with(jwt().jwt(builder -> builder.claim("scope", "product_service")))
                        .locale(Locale.of("ru"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name":"New name product",
                                "typeProductId":0
                                }
                                """))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(content().json("""
                        {
                        "errors": ["Тип продукта должен быть 1=кредит, 2=карта"]
                        }
                        """));
    }

    @Test
    void updateProduct_ProductDoesNotExist_ReturnNotFound() throws Exception {
        MockHttpServletRequestBuilder patch = MockMvcRequestBuilders.patch(PRODUCT_URI, productDTO3.getId())
                .with(jwt().jwt(builder -> builder.claim("scope", "product_service")))
                .locale(Locale.of("ru"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "name":"New name product",
                        "typeProductId":2
                        }
                        """);
        this.mockMvc.perform(patch)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql("/sql/product_tariff_insert.sql")
    void deleteProduct_ProductExists_ReturnNoContent() throws Exception {
        this.mockMvc.perform(delete(PRODUCT_URI, productDTO2.getId())
                        .with(jwt().jwt(builder -> builder.claim("scope", "product_service"))))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteProduct_ProductDoesNotExist_ReturnNotFound() throws Exception {
        this.mockMvc.perform(delete(PRODUCT_URI, UUID.randomUUID())
                        .with(jwt().jwt(builder -> builder.claim("scope", "product_service"))))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_UserIsNotAuthorized_ReturnsForbidden() throws Exception {
        this.mockMvc.perform(delete(PRODUCT_URI, UUID.randomUUID())
                        .with(jwt()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
