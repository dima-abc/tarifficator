package com.test2.users.service.mapper;

import com.test2.users.controller.payload.NewAccount;
import com.test2.users.entity.Account;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
public class AccountMapper {
    public static Account mapToAccount(NewAccount newAccount) {
        if (newAccount == null) {
            return null;
        }
        LocalDate birthDate = newAccount.getBirthDate() != null ? mapToLocalDate(newAccount.getBirthDate()) : null;
        UUID bankId = mapToUUID(newAccount.getBankId());
        return Account.of()
                .bankId(bankId)
                .lastName(newAccount.getLastName())
                .firstName(newAccount.getFirstName())
                .middleName(newAccount.getMiddleName())
                .birthDate(birthDate)
                .passport(newAccount.getPassport())
                .placeBirth(newAccount.getPlaceBirth())
                .phone(newAccount.getPhone())
                .email(newAccount.getEmail())
                .addressRegistered(newAccount.getAddressRegistered())
                .addressLife(newAccount.getAddressLife())
                .build();
    }

    private static LocalDate mapToLocalDate(String localDate) {
        return LocalDate.parse(localDate, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static UUID mapToUUID(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            log.error("Invalid UUID: {}", id, e);
            return null;
        }
    }
}
