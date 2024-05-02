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

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class AccountsRestControllerTestIT {
    @Autowired
    MockMvc mockMvc;
    String uuid1 = "3d491244-4fd6-4368-a97d-d2fb610d8649";
    String uuid2 = "0ea1509a-4ccd-4dc7-b0c9-27040d16af84";
    String uuid3 = "04294245-fd3f-48c8-b70a-7825b90d906a";
    String uuid4 = "140ac82a-e1cd-4169-9c8b-4e8b8ab91a0c";
    String uuid5 = "0c2da3d3-1fbc-4178-881e-ce7368ed5a4b";
    String bankId = "6263be70-e09f-4ca1-a02f-42b2631084a3";

    @Test
    @Sql("/sql/accounts_insert.sql")
    void findAccountByAccountParamReturnIterableAccount() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/accounts")
                .param("lastName", "Петров")
                .param("firstName", "Карп");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [{"bankId":"6263be70-e09f-4ca1-a02f-42b2631084a3","lastName":"Петров",
                        "firstName":"Петр","middleName":"Петрович","birthDate":"1975-01-01",
                        "passport":"1111 111111","placeBirth":"г.Калуга","phone":"71001001900",
                        "email":"petrov@mail.ru","addressRegistered":"г.Калуга","addressLife":"г.Калуга"},
                        {"bankId":"6263be70-e09f-4ca1-a02f-42b2631084a3","lastName":"Карпун",
                        "firstName":"Карп","middleName":"Поликарпович","birthDate":"2004-04-21",
                        "passport":"4444 444444","placeBirth":"г.Майкоп","phone":"74004004400",
                        "email":"karp@mail.ru","addressRegistered":"г.Майкоп","addressLife":"г.Майкоп"}]
                        """, false));
    }

    @Test
    @Sql("/sql/accounts_insert.sql")
    void findAccountByAccountParamReturnIterableAccountEmpty() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/accounts")
                .param("lastName", "XXX")
                .param("firstName", "XXX");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        []
                        """));
    }

    @Test
    void findAccountByAccountParamReturnBadRequest() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/accounts");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createAccountRequestValidReturnNewAccount() throws Exception {
        String headerName = "x-Source";
        UUID uuid = UUID.randomUUID();
        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/accounts")
                .header(headerName, "mail")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"bankId": "6263be70-e09f-4ca1-a02f-42b2631084a3",
                        "firstName": "Ivan",
                        "email": "ivan@email"}
                        """);
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {"bankId":"6263be70-e09f-4ca1-a02f-42b2631084a3","lastName":null,
                        "firstName":"Ivan","middleName":null,"birthDate":null,
                        "passport":null,"placeBirth":null,"phone":null,
                        "email":"ivan@email","addressRegistered":null,"addressLife":null}
                        """));
    }

    @Test
    void createAccountRequestValidReturnBadRequest() throws Exception {
        String headerName = "x-Source";
        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/accounts")
                .header(headerName, "mail")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"firstName": "Ivan"}
                        """);
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(content().json("""
                        {"type":"about:blank","title":"Bad Request",
                        "status":400,
                        "detail":"Запрос содержит ошибки",
                        "instance":"/api/v1/accounts"}
                        """));
    }
}
