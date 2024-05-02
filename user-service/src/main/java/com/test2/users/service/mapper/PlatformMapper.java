package com.test2.users.service.mapper;

import com.test2.users.controller.payload.NewPlatform;
import com.test2.users.entity.Platform;

public class PlatformMapper {

    public static Platform mapToPlatform(NewPlatform newPlatform) {
        if (newPlatform == null) {
            return null;
        }
        return Platform.of()
                .platformName(newPlatform.getPlatformName())
                .bankId(newPlatform.isBankId())
                .lastName(newPlatform.isLastName())
                .firstName(newPlatform.isFirstName())
                .middleName(newPlatform.isMiddleName())
                .birthDate(newPlatform.isBirthDate())
                .passport(newPlatform.isPassport())
                .placeBirth(newPlatform.isPlaceBirth())
                .phone(newPlatform.isPhone())
                .email(newPlatform.isEmail())
                .addressRegistered(newPlatform.isAddressRegistered())
                .addressLife(newPlatform.isAddressLife())
                .build();
    }
}
