package com.test2.users.service;

import com.test2.users.entity.Account;
import com.test2.users.repository.AccountRepository;
import com.test2.users.service.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImplAccountService implements AccountService {
    private final AccountRepository accountRepository;

    @Transactional
    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> findAccountById(String id) {
        UUID uuid = AccountMapper.mapToUUID(id);
        return accountRepository.findById(uuid);
    }

    @Override
    public Iterable<Account> findAccountByAccountParam(Map<String, String> findAccountParam) {
        return accountRepository
                .findAccountByLastNameOrFirstNameOrMiddleNameOrPhoneOrEmail(
                        findAccountParam.get("lastName"),
                        findAccountParam.get("firstName"),
                        findAccountParam.get("middleName"),
                        findAccountParam.get("phone"),
                        findAccountParam.get("email"));
    }
}
