package com.test2.users.service.mapper;

import com.test2.users.controller.payload.NewAccountPayload;
import com.test2.users.entity.Account;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
public class AccountMapper {
    public static Account mapToAccount(NewAccountPayload newAccountPayload) {
        if (newAccountPayload == null) {
            return null;
        }
        LocalDate birthDate = newAccountPayload.getBirthDate() != null ? mapToLocalDate(newAccountPayload.getBirthDate()) : null;
        UUID bankId = mapToUUID(newAccountPayload.getBankId());
        return Account.of()
                .bankId(bankId)
                .lastName(newAccountPayload.getLastName())
                .firstName(newAccountPayload.getFirstName())
                .middleName(newAccountPayload.getMiddleName())
                .birthDate(birthDate)
                .passport(newAccountPayload.getPassport())
                .placeBirth(newAccountPayload.getPlaceBirth())
                .phone(newAccountPayload.getPhone())
                .email(newAccountPayload.getEmail())
                .addressRegistered(newAccountPayload.getAddressRegistered())
                .addressLife(newAccountPayload.getAddressLife())
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
