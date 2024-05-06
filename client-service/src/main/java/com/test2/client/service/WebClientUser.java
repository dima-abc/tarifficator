package com.test2.client.service;

import com.test2.client.payload.user.Account;
import com.test2.client.payload.user.NewAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RequiredArgsConstructor
public class WebClientUser implements UserClientService {
    private final WebClient webClient;

    @Override
    public Mono<Account> createAccount(String header, NewAccount newAccount) {
        return null;
    }

    @Override
    public Mono<Account> findAccount(String accountId) {
        return null;
    }

    @Override
    public Flux<Account> findAllAccounts(Map<String, String> accountParam) {
        return null;
    }
}
