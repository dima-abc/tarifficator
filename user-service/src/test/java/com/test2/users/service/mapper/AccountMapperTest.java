package com.test2.users.service.mapper;

import com.test2.users.controller.payload.NewAccount;
import com.test2.users.entity.Account;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountMapperTest {
    @Test
    void mapToAccountThenReturnAccount() {
        UUID bankId = UUID.randomUUID();
        NewAccount accountPayload = NewAccount.of()
                .bankId(bankId.toString())
                .lastName("Ivanov")
                .firstName("Ivan")
                .middleName("Pertrovich")
                .birthDate("1950-10-22")
                .passport("2222 333333")
                .placeBirth("c.Anapa")
                .phone("71231234567")
                .email("ivan@mail.ru")
                .addressRegistered("c.Moscow")
                .addressLife("c.Omsk")
                .build();
        Account expect = Account.of()
                .bankId(bankId)
                .lastName("Ivanov")
                .firstName("Ivan")
                .middleName("Pertrovich")
                .birthDate(LocalDate.of(1950, 10, 22))
                .passport("2222 333333")
                .placeBirth("c.Anapa")
                .phone("71231234567")
                .email("ivan@mail.ru")
                .addressRegistered("c.Moscow")
                .addressLife("c.Omsk")
                .build();

        Account result = AccountMapper.mapToAccount(accountPayload);
        assertEquals(expect, result);
    }
}