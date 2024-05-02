package com.test2.users.controller;

import com.test2.users.controller.PlatformRestController;
import com.test2.users.controller.payload.UpdatePlatform;
import com.test2.users.entity.Platform;
import com.test2.users.service.PlatformService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlatformRestControllerTest {
    @Mock
    PlatformService platformService;

    @InjectMocks
    PlatformRestController controller;

    @Test
    void getPlatformExistsReturnsPlatform() {
        var platform = Platform.of()
                .id(1L)
                .platformName("Название")
                .bankId(true)
                .build();
        doReturn(Optional.of(platform)).when(this.platformService).findPlatformById(1L);
        var result = this.controller.getPlatform(1L);
        assertEquals(platform, result);
    }

    @Test
    void getPlatformDoesNotExistThrowsNoSuchElementException() {
        var exception = assertThrows(NoSuchElementException.class, () -> this.controller.getPlatform(1L));
        assertEquals("customer.errors.platform.not_found", exception.getMessage());
    }

    @Test
    void findPlatformReturnsPlatform() {
        var platform = Platform.of()
                .id(1L)
                .platformName("Название")
                .bankId(true)
                .build();
        var result = this.controller.findPlatform(platform);
        assertEquals(platform, result);
    }

    @Test
    void updatePlatformRequestIsValidReturnsNoContent() throws BindException {
        var updatePlatform = UpdatePlatform.of()
                .platformName("Новое название")
                .bankId(true)
                .build();
        var bindingResult = new MapBindingResult(Map.of(), "updatePlatform");
        var result = this.controller.updatePlatform(1L, updatePlatform, bindingResult);
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(this.platformService).updatePlatform(1L, updatePlatform);
    }

    @Test
    void deletePlatformReturnsNoContent() {
        var result = this.controller.deletePlatform(1L);
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        verify(this.platformService).deletePlatformById(1L);
    }

    @Test
    void updatePlatformRequestIsInvalidReturnsBadRequest() {
        var updatePlatform = UpdatePlatform.of()
                .platformName("   ")
                .bankId(true)
                .build();
        var bindingResult = new MapBindingResult(Map.of(), "updatePlatform");
        bindingResult.addError(new FieldError("updatePlatform", "platformName", "error"));
        var exception = assertThrows(BindException.class, () -> this.controller.updatePlatform(1L, updatePlatform, bindingResult));
        assertEquals(List.of(new FieldError("updatePlatform", "platformName", "error")), exception.getAllErrors());
        verifyNoInteractions(this.platformService);
    }
}