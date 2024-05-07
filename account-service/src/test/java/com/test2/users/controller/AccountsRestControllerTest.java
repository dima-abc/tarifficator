package com.test2.users.controller;

import com.test2.users.controller.payload.NewAccount;
import com.test2.users.entity.Account;
import com.test2.users.entity.Platform;
import com.test2.users.service.AccountService;
import com.test2.users.service.PlatformService;
import com.test2.users.service.mapper.AccountMapper;
import com.test2.users.validator.PayloadValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountsRestControllerTest {
    @Mock
    AccountService accountService;
    PlatformService platformService = mock(PlatformService.class);
    @Spy
    PayloadValidator payloadValidator = new PayloadValidator(platformService);
    @InjectMocks()
    AccountsRestController controller;

    String uuid1 = "3d491244-4fd6-4368-a97d-d2fb610d8649";
    String uuid2 = "0ea1509a-4ccd-4dc7-b0c9-27040d16af84";
    String uuid3 = "04294245-fd3f-48c8-b70a-7825b90d906a";
    String uuid4 = "140ac82a-e1cd-4169-9c8b-4e8b8ab91a0c";
    String uuid5 = "0c2da3d3-1fbc-4178-881e-ce7368ed5a4b";
    String bankId = "6263be70-e09f-4ca1-a02f-42b2631084a3";

    @Test
    void beanIsNotNull() {
        assertNotNull(controller);
    }

    @Test
    void findAccountByAccountParamThenReturnIterableAccount() {
        UUID accountId1 = UUID.randomUUID();
        UUID accountId2 = UUID.randomUUID();
        Map<String, String> findParam = Map.of(
                "lastName", "Petrov",
                "firstName", "Ivan");
        Account account1 = Account.of()
                .id(accountId1)
                .lastName("Petrov")
                .firstName("Petr")
                .build();
        Account account2 = Account.of()
                .id(accountId2)
                .lastName("Ivanov")
                .firstName("Ivan")
                .build();
        doReturn(List.of(account1, account2))
                .when(this.accountService).findAccountByAccountParam(findParam);
        Iterable<Account> result = this.controller.findAccountByAccountParam(findParam);
        assertThat(List.of(account1, account2)).isEqualTo(result);
    }

    @Test
    void findAccountByAccountParamThenReturnIterableEmpty() {
        Map<String, String> findParam = Map.of(
                "lastName", "Petrov",
                "firstName", "Ivan");
        doReturn(List.of())
                .when(this.accountService).findAccountByAccountParam(findParam);
        Iterable<Account> result = this.controller.findAccountByAccountParam(findParam);
        assertThat(result).isEmpty();
    }

    @Test
    void findAccountByAccountParamThenReturnNoSuchException() {
        String messageError = "customer.errors.account.find_param";
        assertThatThrownBy(() -> this.controller.findAccountByAccountParam(Map.of()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining(messageError);
    }

    @Test
    void createAccountThenRequestValidReturnNewContent() throws BindException {
        String headerValue = "mail";
        Platform platform = Platform.of()
                .platformName(headerValue)
                .firstName(true)
                .email(true)
                .build();
        NewAccount payload = NewAccount.of()
                .bankId(bankId)
                .firstName("Ivan")
                .email("ivan@mail.ru")
                .build();
        Account accountNew = AccountMapper.mapToAccount(payload);
        UUID accountId = UUID.fromString(uuid1);
        UUID uuidBankId = UUID.fromString(bankId);
        Account account = Account.of()
                .id(accountId)
                .bankId(uuidBankId)
                .firstName("Ivan")
                .email("ivan@mail.ru")
                .build();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString("http://localhost");
        doReturn(Optional.of(platform)).when(platformService)
                .findPlatformByName(headerValue);
        doReturn(account).when(this.accountService)
                .createAccount(accountNew);
        ResponseEntity<?> result = this.controller.createAccount(headerValue, payload, uriComponentsBuilder);

        assertThat(result).isNotNull();
        assertThat(HttpStatus.CREATED).isEqualTo(result.getStatusCode());
        assertThat(URI.create("http://localhost/api/v1/accounts/3d491244-4fd6-4368-a97d-d2fb610d8649"))
                .isEqualTo(result.getHeaders().getLocation());
        assertThat(account).isEqualTo(result.getBody());

        verify(this.accountService).createAccount(accountNew);
        verifyNoMoreInteractions(this.accountService);
    }

    @Test
    void createAccount_RequestIsInvalid_ReturnBadRequest() {
        String headerValue = "mail";
        Platform platform = Platform.of()
                .platformName(headerValue)
                .firstName(true)
                .email(true)
                .build();
        NewAccount payload = NewAccount.of()
                .firstName("Ivan")
                .build();
        doReturn(Optional.of(platform)).when(platformService)
                .findPlatformByName(headerValue);
        String messageError = "customer.errors.account.email";
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString("http://localhost");
        assertThatThrownBy(() -> this.controller.createAccount(headerValue, payload, uriComponentsBuilder))
                .isInstanceOf(BindException.class)
                .hasMessageContaining(messageError);
        verifyNoInteractions(accountService);
    }
}