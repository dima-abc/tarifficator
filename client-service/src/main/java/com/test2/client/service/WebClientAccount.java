package com.test2.client.service;

import com.test2.client.payload.account.Account;
import com.test2.client.payload.account.NewAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RequiredArgsConstructor
public class WebClientAccount implements AccountClientService {
    public static final String HEADER_ACCOUNT = "x-Source";
    private static final String BASE_URL_ACCOUNTS = "api/v1/accounts";
    private static final String BASE_URL_ACCOUNTS_ID = "api/v1/accounts/{id}";
    private final WebClient webClient;

    @Override
    public Mono<Account> createAccount(String headerValue, NewAccount newAccount, String token) {
        return this.webClient
                .post()
                .uri(BASE_URL_ACCOUNTS)
                .header(HEADER_ACCOUNT, headerValue)
                .header(HttpHeaders.AUTHORIZATION, token)
                .bodyValue(newAccount)
                .retrieve()
                .bodyToMono(Account.class);
    }

    @Override
    public Mono<Account> findAccount(String accountId, String token) {
        return this.webClient
                .get()
                .uri(BASE_URL_ACCOUNTS_ID, accountId)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(Account.class);
    }

    @Override
    public Flux<Account> findAllAccounts(Map<String, String> accountParam, String token) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.setAll(accountParam);
        return this.webClient
                .mutate()
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder.path(BASE_URL_ACCOUNTS)
                        .queryParams(queryParams)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToFlux(Account.class);
    }
}
