package com.test2.users.repository;

import com.test2.users.entity.Account;
import com.test2.users.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Sql("/sql/accounts_insert.sql")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;
    private Iterator<Account> allAccount;

    @BeforeEach
    public void getAllAccount() {
        this.allAccount = accountRepository.findAll().iterator();
    }

    @Test
    void accountIsNotEmpty() {
        assertThat(accountRepository).isNotNull();
    }

    @Test
    void accountFindNotParamThenReturnEmpty() {
        Iterable<Account> accountList = accountRepository
                .findAccountByLastNameOrFirstNameOrMiddleNameOrPhoneOrEmail(null, null,
                        null, null, null);
        assertThat(accountList).isEmpty();
    }

    @Test
    void accountFindParamLostNameThenReturnListOneAccount() {
        Account accountExpected = allAccount.next();
        String lostName = accountExpected.getLastName();
        Iterable<Account> accountList = accountRepository
                .findAccountByLastNameOrFirstNameOrMiddleNameOrPhoneOrEmail(lostName, null,
                        null, null, null);
        Iterable<Account> expected = List.of(accountExpected);
        assertThat(accountList).isEqualTo(expected);
    }

    @Test
    void accountFindParamLostNameAndFirstNameThenReturnListTwoAccount() {
        Account accountExpected1 = allAccount.next();
        Account accountExpected2 = allAccount.next();
        String lostName = accountExpected1.getLastName();
        String firstName = accountExpected2.getFirstName();
        Iterable<Account> accountList = accountRepository
                .findAccountByLastNameOrFirstNameOrMiddleNameOrPhoneOrEmail(lostName, firstName,
                        null, null, null);
        Iterable<Account> expected = List.of(accountExpected1, accountExpected2);
        assertThat(accountList).isEqualTo(expected);
    }

    @Test
    void accountFindParamLostNameAndFirstNameMiddleNameThenReturnListThreeAccount() {
        Account accountExpected1 = allAccount.next();
        Account accountExpected2 = allAccount.next();
        Account accountExpected3 = allAccount.next();
        String lostName = accountExpected1.getLastName();
        String firstName = accountExpected2.getFirstName();
        String middleName = accountExpected3.getMiddleName();
        Iterable<Account> accountList = accountRepository
                .findAccountByLastNameOrFirstNameOrMiddleNameOrPhoneOrEmail(lostName, firstName,
                        middleName, null, null);
        Iterable<Account> expected = List.of(accountExpected1, accountExpected2, accountExpected3);
        assertThat(accountList).isEqualTo(expected);
    }

    @Test
    void accountFindParamLostNameAndFirstNameMiddleNamePhoneThenReturnListForeAccount() {
        Account accountExpected1 = allAccount.next();
        Account accountExpected2 = allAccount.next();
        Account accountExpected3 = allAccount.next();
        Account accountExpected4 = allAccount.next();
        String lostName = accountExpected1.getLastName();
        String firstName = accountExpected2.getFirstName();
        String middleName = accountExpected3.getMiddleName();
        String phone = accountExpected4.getPhone();
        Iterable<Account> accountList = accountRepository
                .findAccountByLastNameOrFirstNameOrMiddleNameOrPhoneOrEmail(lostName, firstName,
                        middleName, phone, null);
        Iterable<Account> expected = List.of(accountExpected1, accountExpected2, accountExpected3, accountExpected4);
        assertThat(accountList).isEqualTo(expected);
    }

    @Test
    void accountFindParamLostNameAndFirstNameMiddleNamePhoneEmailThenReturnListFiveAccount() {
        Account accountExpected1 = allAccount.next();
        Account accountExpected2 = allAccount.next();
        Account accountExpected3 = allAccount.next();
        Account accountExpected4 = allAccount.next();
        Account accountExpected5 = allAccount.next();
        String lostName = accountExpected1.getLastName();
        String firstName = accountExpected2.getFirstName();
        String middleName = accountExpected3.getMiddleName();
        String phone = accountExpected4.getPhone();
        String email = accountExpected5.getEmail();
        Iterable<Account> accountList = accountRepository
                .findAccountByLastNameOrFirstNameOrMiddleNameOrPhoneOrEmail(lostName, firstName,
                        middleName, phone, email);
        Iterable<Account> expected = List.of(accountExpected1, accountExpected2, accountExpected3, accountExpected4, accountExpected5);
        assertThat(accountList).isEqualTo(expected);
    }
}