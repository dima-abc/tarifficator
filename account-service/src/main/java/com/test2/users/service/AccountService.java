package com.test2.users.service;

import com.test2.users.entity.Account;

import java.util.Map;
import java.util.Optional;

public interface AccountService {

    Account createAccount(Account account);

    Optional<Account> findAccountById(String id);

    Iterable<Account> findAccountByAccountParam(Map<String, String> findAccountParam);
}
