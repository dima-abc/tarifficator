package com.test2.users.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class PlatformRestControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql("/sql/platform_insert.sql")
    void findPlatformThenReturnPlatform() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/platforms/101");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "id": 101,
                                    "platformName": "email1",
                                    "bankId": true,
                                    "lastName": true
                                }""")
                );
    }

    @Test
    void findPlatformDoesNotExistReturnsNotFound() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/platforms/201");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    @Sql("/sql/platform_insert.sql")
    void updatePlatformRequestIsValidReturnsNoContent() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.put("/api/v1/platforms/101")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "platformName": "newEmail",
                            "bankId": false
                        }""");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isNoContent()
                );
    }

    @Test
    @Sql("/sql/platform_insert.sql")
    void updatePlatformRequestIsInvalidReturnsBadRequest() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.put("/api/v1/platforms/101")
                .locale(Locale.forLanguageTag("ru"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "platformName": "   "
                        }""");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON)
                );
    }

    @Test
    void updatePlatformDoesNotExistReturnsNotFound() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.put("/api/v1/platforms/101")
                .locale(Locale.forLanguageTag("ru"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "platformName": "Новое название",
                            "bankId": true
                        }""");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isNotFound()
                );
    }

    @Test
    @Sql("/sql/platform_insert.sql")
    void deletePlatformExistsReturnsNoContent() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.delete("/api/v1/platforms/101");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isNoContent()
                );
    }

    @Test
    void deletePlatformDoesNotExist_ReturnsNotFound() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.delete("/api/v1/platforms/101");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isNotFound()
                );
    }
}
