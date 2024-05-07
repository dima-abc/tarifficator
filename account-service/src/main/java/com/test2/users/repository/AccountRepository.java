package com.test2.users.repository;

import com.test2.users.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AccountRepository extends CrudRepository<Account, UUID> {
    Iterable<Account> findAccountByLastNameOrFirstNameOrMiddleNameOrPhoneOrEmail(String lastName,
                                                                             String firstName,
                                                                             String middleName,
                                                                             String phone,
                                                                             String email);
}
