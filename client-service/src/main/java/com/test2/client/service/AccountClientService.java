package com.test2.client.service;

import com.test2.client.payload.account.Account;
import com.test2.client.payload.account.NewAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface AccountClientService {
    Mono<Account> createAccount(String headerValue, NewAccount newAccount);

    Mono<Account> findAccount(String accountId);

    Flux<Account> findAllAccounts(Map<String, String> accountParam);
}
