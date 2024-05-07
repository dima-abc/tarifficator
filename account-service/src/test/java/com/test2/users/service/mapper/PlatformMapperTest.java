package com.test2.users.service.mapper;

import com.test2.users.controller.payload.NewPlatform;
import com.test2.users.entity.Platform;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlatformMapperTest {
    @Test
    void test_all_fields_set_to_corresponding_values() {
        NewPlatform newPlatform = NewPlatform.of()
                .platformName("Test Platform")
                .bankId(true)
                .lastName(true)
                .firstName(true)
                .middleName(true)
                .birthDate(true)
                .passport(true)
                .placeBirth(true)
                .phone(true)
                .email(true)
                .addressRegistered(true)
                .addressLife(true)
                .build();

        Platform platform = PlatformMapper.mapToPlatform(newPlatform);

        assertNotNull(platform);
        assertEquals("Test Platform", platform.getPlatformName());
        assertTrue(platform.isBankId());
        assertTrue(platform.isLastName());
        assertTrue(platform.isFirstName());
        assertTrue(platform.isMiddleName());
        assertTrue(platform.isBirthDate());
        assertTrue(platform.isPassport());
        assertTrue(platform.isPlaceBirth());
        assertTrue(platform.isPhone());
        assertTrue(platform.isEmail());
        assertTrue(platform.isAddressRegistered());
        assertTrue(platform.isAddressLife());
    }

    @Test
    void test_all_boolean_fields_set_to_false_when_all_fields_are_null() {
        NewPlatform newPlatform = NewPlatform.of().build();

        Platform platform = PlatformMapper.mapToPlatform(newPlatform);

        assertNotNull(platform);
        assertFalse(platform.isBankId());
        assertFalse(platform.isLastName());
        assertFalse(platform.isFirstName());
        assertFalse(platform.isMiddleName());
        assertFalse(platform.isBirthDate());
        assertFalse(platform.isPassport());
        assertFalse(platform.isPlaceBirth());
        assertFalse(platform.isPhone());
        assertFalse(platform.isEmail());
        assertFalse(platform.isAddressRegistered());
        assertFalse(platform.isAddressLife());
    }
}