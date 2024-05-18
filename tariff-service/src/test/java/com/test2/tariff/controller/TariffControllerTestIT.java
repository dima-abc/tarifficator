package com.test2.tariff.controller;

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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class TariffControllerTestIT {
    private static final String TARIFF1 = "ecbfd764-b3cf-4207-a86a-04ca643f7c88";
    private static final String TARIFF2 = "df32bd16-82ad-48b4-952f-f561a947593c";
    private static final String TARIFF_URI = "/api/v1/tariffs/{tariffId}";

    @Autowired
    MockMvc mockMvc;


    @Test
    void dependencyNotNull() {
        assertNotNull(this.mockMvc);
    }

    @Test
    @Sql("/sql/tariff_product_insert.sql")
    void findTariff_TariffExists_ReturnTariff() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get(TARIFF_URI, TARIFF1)
                .with(jwt().jwt(builder -> builder.claim("scope", "tariff_service")));
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                "id":"ecbfd764-b3cf-4207-a86a-04ca643f7c88",
                                "name": "Tariff name1",
                                "startDate":"2020-02-20",
                                "endDate":"2024-04-08",
                                "description": "Tariff1 detail",
                                "rate": 25.0,
                                "version": 1
                                }""")
                );
    }

    @Test
    void findTariff_TariffNotExists_ReturnNotFound() throws Exception {
        this.mockMvc.perform(get(TARIFF_URI, TARIFF1)
                        .with(jwt().jwt(builder -> builder.claim("scope", "tariff_service"))))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql("/sql/tariff_product_insert.sql")
    void updateTariff_RequestIsValid_ReturnNoContent() throws Exception {
        this.mockMvc.perform(patch(TARIFF_URI, TARIFF2)
                        .with(jwt().jwt(builder -> builder.claim("scope", "tariff_service")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name": "new name tariff"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @Sql("/sql/tariff_product_insert.sql")
    void updateTariff_RequestIsInvalid_ReturnBadRequest() throws Exception {
        MockHttpServletRequestBuilder patch = patch(TARIFF_URI, TARIFF1)
                .with(jwt().jwt(builder -> builder.claim("scope", "tariff_service")))
                .locale(Locale.of("ru"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "name": "  "
                        }
                        """);
        this.mockMvc.perform(patch)
                .andDo(print())
                .andExpectAll(status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json("""
                                {
                                "errors": ["Имя тарифа должно быть от 3 до 255 символов"]
                                }
                                """));
    }

    @Test
    void updateTariff_TariffDoesNotExist_ReturnsNotFound() throws Exception {
        MockHttpServletRequestBuilder patch = patch(TARIFF_URI, TARIFF2)
                .with(jwt().jwt(builder -> builder.claim("scope", "tariff_service")))
                .locale(Locale.of("ru"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "name": "new name tariff"
                        }
                        """);
        this.mockMvc.perform(patch)
                .andDo(print())
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    @Sql("/sql/tariff_product_insert.sql")
    void deleteTariff_TariffExists_ReturnNoContent() throws Exception {
        this.mockMvc.perform(delete(TARIFF_URI, TARIFF1)
                        .with(jwt().jwt(builder -> builder.claim("scope", "tariff_service"))))
                .andDo(print())
                .andExpectAll(status().isNoContent());
    }

    @Test
    void deleteTariff_TariffDoesNotExist_ReturnNotFound() throws Exception {
        this.mockMvc.perform(delete(TARIFF_URI, TARIFF2)
                        .with(jwt().jwt(builder -> builder.claim("scope", "tariff_service"))))
                .andDo(print())
                .andExpectAll(status().isNotFound());
    }

    @Test
    void deleteTariff_UsersIsNotAuthorized_ReturnForbidden() throws Exception {
        this.mockMvc.perform(delete(TARIFF_URI, TARIFF2)
                        .with(jwt()))
                .andDo(print())
                .andExpectAll(status().isForbidden());
    }
}
