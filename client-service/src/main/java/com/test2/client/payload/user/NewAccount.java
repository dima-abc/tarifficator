package com.test2.client.payload.user;

public record NewAccount(String bankId,
                         String firstName,
                         String lastName,
                         String middleName,
                         String birthDate,
                         String passport,
                         String placeBirth,
                         String phone,
                         String email,
                         String addressRegistered,
                         String addressLife) {
}
