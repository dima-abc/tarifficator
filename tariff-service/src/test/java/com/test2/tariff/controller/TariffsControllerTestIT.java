package com.test2.tariff.controller;

import com.test2.tariff.payload.TariffDTO;
import org.hamcrest.Matchers;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class TariffsControllerTestIT {
    TariffDTO tariffDTO1 = TariffDTO.of()
            .id("ecbfd764-b3cf-4207-a86a-04ca643f7c88")
            .name("Tariff name1").startDate("2020-02-20").endDate("2024-04-08")
            .description("Tariff1 detail").rate(25.0D).version(1)
            .build();
    TariffDTO tariffDTO2 = TariffDTO.of()
            .id("df32bd16-82ad-48b4-952f-f561a947593c")
            .name("Tariff name2").startDate("2021-02-20").endDate("2023-03-01")
            .description("Tariff2 detail").rate(15.0D).version(2)
            .build();
    private static final String TARIFFS_URI = "/api/v1/tariffs";
    @Autowired
    MockMvc mockMvc;

    @Test
    void dependencyNotNull() {
        assertNotNull(this.mockMvc);
    }

    @Test
    @Sql("/sql/tariff_product_insert.sql")
    void findAllTariff_ReturnsAllTariffs() throws Exception {
        this.mockMvc.perform(get(TARIFFS_URI)
                .with(jwt().jwt(builder -> builder.claim("scope", "tariff_service"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content().json("""
                                [
                                {"id":"ecbfd764-b3cf-4207-a86a-04ca643f7c88", "name":"Tariff name1", "startDate":"2020-02-20", "endDate":"2024-04-08", "description":"Tariff1 detail", "rate":25.0, "version":1},
                                {"id":"df32bd16-82ad-48b4-952f-f561a947593c", "name":"Tariff name2", "startDate":"2021-02-20", "endDate":"2023-03-01", "description":"Tariff2 detail", "rate":15.0, "version":2}
                                ]
                                """));
    }

    @Test
    void findAllTariffs_ReturnsEmpty() throws Exception {
        this.mockMvc.perform(get(TARIFFS_URI)
                .with(jwt().jwt(builder -> builder.claim("scope", "tariff_service"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                                                []
                        """));
    }

    @Test
    void createTariff_RequestIsValid_ReturnsNewTariff() throws Exception {
        var requestBuild = MockMvcRequestBuilders.post(TARIFFS_URI)
                .with(jwt().jwt(builder -> builder.claim("scope", "tariff_service")))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name":"New tariff", "startDate":"2024-02-20", "endDate":"2026-04-08",
                        "description":"New tariff detail", "rate":25.0}
                        """);
        this.mockMvc.perform(requestBuild)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, Matchers.containsString("http://localhost/api/v1/tariffs/")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                        "name":"New tariff",
                        "startDate":"2024-02-20",
                        "endDate":"2026-04-08",
                        "description":"New tariff detail",
                        "rate":25.0
                        }"""));
    }

    @Test
    void createTariff_RequestIsInvalid_ReturnsProblemDetail() throws Exception {
        var requestBuild = MockMvcRequestBuilders.post(TARIFFS_URI)
                .with(jwt().jwt(builder -> builder.claim("scope", "tariff_service")))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"startDate":"2024-02-20", "endDate":"2026-04-08",
                        "description":"New tariff detail", "rate":25.0}
                        """)
                .locale(Locale.of("ru", "RU"));
        this.mockMvc.perform(requestBuild)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(content().json("""
                                   {
                                        "errors":["Имя тарифа должно быть указано"]
                                   }
                        """));
    }
}
