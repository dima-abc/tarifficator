package com.test2.users.validator;

import com.test2.users.controller.payload.NewAccount;
import com.test2.users.entity.Platform;
import com.test2.users.service.PlatformService;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class PayloadValidatorTest {
    PlatformService platformService = mock(PlatformService.class);
    private static final String HEADER_MAIL = "mail";
    private static final String HEADER_MOBILE = "mobile";
    private static final String HEADER_BANK = "bank";
    private static final String HEADER_GOSUSLUGI = "gosuslugi";

    @Test
    public void test_valid_header_and_valid_payload() {
        Platform platform = Platform.of()
                .id(1L)
                .platformName(HEADER_MAIL)
                .bankId(true)
                .firstName(true)
                .lastName(true)
                .build();
        doReturn(Optional.of(platform))
                .when(platformService)
                .findPlatformByName(HEADER_MAIL);
        PayloadValidator payloadValidator = new PayloadValidator(platformService);
        UUID bankId = UUID.randomUUID();
        NewAccount payload = NewAccount.of()
                .bankId(bankId.toString())
                .firstName("Petr")
                .lastName("Vasin")
                .email("vasin@email.com")
                .build();
        BindingResult result = payloadValidator.isValid(HEADER_MAIL, payload);
        assertFalse(result.hasErrors());
    }

    @Test
    public void test_valid_header_and_invalid_payload() {
        Platform platform = Platform.of()
                .platformName(HEADER_BANK)
                .firstName(true)
                .lastName(true)
                .build();
        doReturn(Optional.of(platform))
                .when(platformService)
                .findPlatformByName(HEADER_BANK);
        PayloadValidator payloadValidator = new PayloadValidator(platformService);
        NewAccount payload = NewAccount.of()
                .firstName("John")
                .lastName("")
                .build();
        BindingResult result = payloadValidator.isValid(HEADER_BANK, payload);
        assertTrue(result.hasErrors());
    }

    @Test
    public void test_valid_header_and_payload_with_required_fields() {
        Platform platform = Platform.of()
                .platformName(HEADER_GOSUSLUGI)
                .bankId(true)
                .firstName(true)
                .lastName(true)
                .build();
        doReturn(Optional.of(platform))
                .when(platformService)
                .findPlatformByName(HEADER_GOSUSLUGI);
        PayloadValidator payloadValidator = new PayloadValidator(platformService);
        UUID bankId = UUID.randomUUID();
        NewAccount payload = NewAccount.of()
                .bankId(bankId.toString())
                .firstName("John")
                .lastName("Doe")
                .build();
        BindingResult result = payloadValidator.isValid(HEADER_GOSUSLUGI, payload);
        assertFalse(result.hasErrors());
    }

    @Test
    public void test_valid_header_with_required_no_such_element_exception() {
        PayloadValidator payloadValidator = new PayloadValidator(platformService);
        UUID bankId = UUID.randomUUID();
        NewAccount payload = NewAccount.of()
                .bankId(bankId.toString())
                .firstName("John")
                .lastName("Doe")
                .build();
        assertThrows(NoSuchElementException.class,
                () -> payloadValidator.isValid(HEADER_GOSUSLUGI, payload));
    }
}