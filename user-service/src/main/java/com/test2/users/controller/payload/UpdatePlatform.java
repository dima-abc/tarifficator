package com.test2.users.controller.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "of")
public class UpdatePlatform {
    @NotNull(message = "{customer.errors.platform.platform_name_is_null}")
    @NotBlank(message = "{customer.errors.platform.platform_name_is_blank}")
    private String platformName;
    private boolean bankId;
    private boolean lastName;
    private boolean firstName;
    private boolean middleName;
    private boolean birthDate;
    private boolean passport;
    private boolean placeBirth;
    private boolean phone;
    private boolean email;
    private boolean addressRegistered;
    private boolean addressLife;
}
