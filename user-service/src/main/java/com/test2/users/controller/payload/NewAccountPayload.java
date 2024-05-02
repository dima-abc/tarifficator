package com.test2.users.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO модель для сохранения учетной записи по x-Source = mail
 * обязательные поля только: имя и email
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "of")
public class NewAccountPayload {
    private String bankId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String birthDate;
    private String passport;
    private String placeBirth;
    private String phone;
    private String email;
    private String addressRegistered;
    private String addressLife;
}
