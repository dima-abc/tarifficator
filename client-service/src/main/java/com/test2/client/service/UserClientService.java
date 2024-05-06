package com.test2.client.service;

import com.test2.client.payload.user.Account;
import com.test2.client.payload.user.NewAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface UserClientService {
    Mono<Account> createAccount(String header, NewAccount newAccount);

    Mono<Account> findAccount(String accountId);

    Flux<Account> findAllAccounts(Map<String, String> accountParam);
}
