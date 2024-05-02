package com.test2.users.controller;

import com.test2.users.entity.Account;
import com.test2.users.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AccountRestControllerTest {
    @Mock
    AccountService accountService;

    @InjectMocks
    AccountRestController controller;

    @Test
    void getAccountThenReturnProduct() {
        UUID accountId = UUID.randomUUID();
        UUID bankId = UUID.randomUUID();
        Account account = Account.of()
                .id(accountId)
                .bankId(bankId)
                .firstName("Ivan")
                .build();
        doReturn(Optional.of(account)).when(this.accountService).findAccountById(accountId.toString());
        Account result = this.controller.getAccount(accountId.toString());
        assertEquals(account, result);
    }

    @Test
    void getAccountNotExistsThenThrowNoSuchElementException() {
        String messageError = "Учетная запись не найдена";
        assertThatThrownBy(() -> this.controller.getAccount("abc"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining(messageError);
    }

    @Test
    void findAccountThenReturnAccount() {
        UUID accountId = UUID.randomUUID();
        UUID bankId = UUID.randomUUID();
        Account account = Account.of()
                .id(accountId)
                .bankId(bankId)
                .firstName("Ivan")
                .build();
        Account result = this.controller.findAccount(account);
        assertThat(result).isEqualTo(account);
    }
}