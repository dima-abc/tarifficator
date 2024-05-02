package com.test2.users.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class PlatformsRestControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql("/sql/platform_insert.sql")
    void findPlatformReturnsPlatformList() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/platforms")
                .param("p-name", "email1");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                    {
                                    "platformName": "email1",
                                    "bankId": true,
                                    "lastName": true
                                    }
                                ]""")
                );
    }

    @Test
    void createPlatformRequestIsValidReturnsNewPlatform() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/platforms")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "platformName": "newPlatform",
                        "bankId": true
                        }""");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "platformName": "newPlatform",
                                    "bankId": true
                                }"""));
    }

    @Test
    void createPlatformRequestIsInvalidReturnsProblemDetail() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/platforms")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"platformName": "  "}
                        """)
                .locale(Locale.forLanguageTag("ru"));
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json("""
                                {
                                    "detail":"Запрос содержит ошибки"
                                }"""));
    }
}
