package com.test2.users.service;

import com.test2.users.entity.Account;
import com.test2.users.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImplAccountServiceTest {
    @Mock
    AccountRepository accountRepository;
    @InjectMocks
    ImplAccountService service;

    UUID id = UUID.randomUUID();
    UUID bankId = UUID.randomUUID();
    String lastName = "Петров";
    String firstName = "Никоглай";
    String middleName = "Виктор ович";
    LocalDate birthDate = LocalDate.of(1987, 02, 24);
    String passport = "1111 111111";
    String placeBirth = "г.Грозный";
    String phone = "79060050050";
    String email = "petrov@mail.ru";
    String addressRegistered = "г.Кемерово";
    String addressLife = "г.Минск";

    @Test
    void createAccountThenReturnCreateAccount() {
        doReturn(new Account(id, bankId, lastName, firstName,
                middleName, birthDate, passport, placeBirth,
                phone, email, addressRegistered, addressLife))
                .when(accountRepository).save(new Account(null, bankId, lastName, firstName,
                        middleName, birthDate, passport, placeBirth,
                        phone, email, addressRegistered, addressLife));

        Account result = this.service.createAccount(new Account(null, bankId, lastName, firstName,
                middleName, birthDate, passport, placeBirth,
                phone, email, addressRegistered, addressLife));

        assertEquals(new Account(id, bankId, lastName, firstName,
                middleName, birthDate, passport, placeBirth,
                phone, email, addressRegistered, addressLife), result);
        verify(this.accountRepository).save(new Account(null, bankId, lastName, firstName,
                middleName, birthDate, passport, placeBirth,
                phone, email, addressRegistered, addressLife));
        verifyNoMoreInteractions(this.accountRepository);
    }

    @Test
    void findAccountByIdThenReturnNotEmptyOptional() {
        UUID id = UUID.randomUUID();
        Account account = new Account(id, bankId, lastName, firstName,
                middleName, birthDate, passport, placeBirth,
                phone, email, addressRegistered, addressLife);
        doReturn(Optional.of(account)).when(this.accountRepository)
                .findById(id);

        Optional<Account> result = this.service.findAccountById(id.toString());

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(account, result.orElseThrow());
        verify(this.accountRepository).findById(id);
        verifyNoMoreInteractions(this.accountRepository);
    }

    @Test
    void findAccountByIdThenReturnEmptyOptional() {
        UUID id = UUID.randomUUID();
        Optional<Account> result = this.service.findAccountById(id.toString());

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(this.accountRepository).findById(id);
        verifyNoMoreInteractions(this.accountRepository);
    }

    @Test
    void findAccountByAccountParamThenReturnIterableAccountNotEmpty() {
        Iterable<Account> accounts = LongStream.range(1L, 5L)
                .mapToObj(i -> new Account(UUID.randomUUID(), UUID.randomUUID(), lastName + i, firstName + i,
                        middleName + i, birthDate, passport, placeBirth,
                        phone + i, email + i, addressRegistered, addressLife))
                .toList();
        Map<String, String> findAccountParam = Map.of(
                "lastName", lastName + 1L,
                "firstName", firstName + 2L,
                "middleName", middleName + 3L,
                "phone", phone + 4L,
                "email", email + 5L);
        doReturn(accounts).when(this.accountRepository)
                .findAccountByLastNameOrFirstNameOrMiddleNameOrPhoneOrEmail(
                        lastName + 1L, firstName + 2L,
                        middleName + 3L, phone + 4L, email + 5L);

        Iterable<Account> result = this.service.findAccountByAccountParam(findAccountParam);

        assertEquals(accounts, result);
        verify(this.accountRepository)
                .findAccountByLastNameOrFirstNameOrMiddleNameOrPhoneOrEmail(
                        lastName + 1L, firstName + 2L,
                        middleName + 3L, phone + 4L, email + 5L);
        verifyNoMoreInteractions(this.accountRepository);
    }

    @Test
    void findAccountByAccountParamThenReturnIterableAccountEmpty() {
        Map<String, String> findAccountParam = Map.of();

        Iterable<Account> result = this.service.findAccountByAccountParam(findAccountParam);

        assertEquals(List.of(), result);
        verify(this.accountRepository)
                .findAccountByLastNameOrFirstNameOrMiddleNameOrPhoneOrEmail(null, null,
                        null, null, null);
        verifyNoMoreInteractions(this.accountRepository);
    }
}