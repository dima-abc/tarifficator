package com.test2.client.service;

import com.test2.client.payload.account.Account;
import com.test2.client.payload.account.NewAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface AccountClientService {
    Mono<Account> createAccount(String headerValue, NewAccount newAccount, String token);

    Mono<Account> findAccount(String accountId, String token);

    Flux<Account> findAllAccounts(Map<String, String> accountParam, String token);
}
