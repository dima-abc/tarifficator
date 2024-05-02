package com.test2.users.controller;

import com.test2.users.entity.Account;
import com.test2.users.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/accounts/{accountId}")
@RequiredArgsConstructor
public class AccountRestController {
    private final AccountService accountService;

    @ModelAttribute("account")
    public Account getAccount(@PathVariable("accountId") String accountId) {
        return this.accountService.findAccountById(accountId)
                .orElseThrow(() -> new NoSuchElementException("Учетная запись не найдена"));
    }

    @GetMapping
    public Account findAccount(@ModelAttribute("account") Account account) {
        return account;
    }
}
