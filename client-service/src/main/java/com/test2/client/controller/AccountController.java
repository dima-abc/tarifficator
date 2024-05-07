package com.test2.client.controller;

import com.test2.client.payload.account.Account;
import com.test2.client.payload.account.NewAccount;
import com.test2.client.service.AccountClientService;
import com.test2.client.service.WebClientAccount;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("api/v1/client/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountClientService accountService;

    @PostMapping()
    public Mono<ResponseEntity<?>> createAccount(@RequestHeader(WebClientAccount.HEADER_ACCOUNT) String headerValue,
                                                 @Valid @RequestBody NewAccount newAccount,
                                                 UriComponentsBuilder uriBuilder) {
        return accountService.createAccount(headerValue, newAccount)
                .map(account -> ResponseEntity
                        .created(uriBuilder.replacePath("/api/v1/client/accounts/{id}")
                                .build(account.id().toString()))
                        .body(account));
    }

    @GetMapping("/{id}")
    public Mono<Account> getAccount(@PathVariable("id") String id) {
        return accountService.findAccount(id);
    }

    @GetMapping()
    public Flux<Account> getAllAccounts(@RequestParam(required = false) Map<String, String> accountParams) {
        return accountService.findAllAccounts(accountParams);
    }
}
