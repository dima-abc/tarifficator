package com.test2.client.payload.user;

import java.time.LocalDate;
import java.util.UUID;

public record Account(UUID id,
                      UUID bankId,
                      String lastName,
                      String firstName,
                      String middleName,
                      LocalDate birthDate,
                      String passport,
                      String placeBirth,
                      String phone,
                      String email,
                      String addressRegistered,
                      String addressLife) {
}
