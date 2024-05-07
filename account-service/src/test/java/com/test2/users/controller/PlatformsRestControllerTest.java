package com.test2.users.controller;

import com.test2.users.controller.payload.NewPlatform;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlatformsRestControllerTest {
    @Mock
    PlatformService platformService;

    @InjectMocks
    PlatformsRestController controller;

    @Test
    void findPlatformReturnsPlatformList() {
        String platformName = "mail";
        doReturn(List.of(
                Platform.of()
                        .id(1L)
                        .platformName("first mail")
                        .bankId(true)
                        .build(),
                Platform.of()
                        .id(2L)
                        .platformName("second mail")
                        .bankId(true)
                        .build()))
                .when(this.platformService).findAllPlatform("mail");
        Iterable<Platform> result = this.controller.findByName(platformName);
        assertEquals(List.of(
                        Platform.of()
                                .id(1L)
                                .platformName("first mail")
                                .bankId(true)
                                .build(),
                        Platform.of()
                                .id(2L)
                                .platformName("second mail")
                                .bankId(true)
                                .build()),
                result);
    }

    @Test
    void createPlatformRequestIsValidReturnsNoContent() throws BindException {
        var newPlatform = NewPlatform.of()
                .platformName("mail")
                .build();
        var bindingResult = new MapBindingResult(Map.of(), "newPlatform");
        var uriComponentsBuilder = UriComponentsBuilder.fromUriString("http://localhost");

        doReturn(Platform.of()
                .id(1L)
                .platformName("mail")
                .build())
                .when(this.platformService).createPlatform(newPlatform);
        var result = this.controller.createPlatform(newPlatform, bindingResult, uriComponentsBuilder);
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(URI.create("http://localhost/api/v1/platforms/1"), result.getHeaders().getLocation());
        assertEquals(Platform.of()
                .id(1L)
                .platformName("mail")
                .build(), result.getBody());
        verify(this.platformService).createPlatform(newPlatform);
        verifyNoMoreInteractions(this.platformService);
    }

    @Test
    void createPlatformRequestIsInvalidReturnsBadRequest() {
        var newPlatform = NewPlatform.of()
                .platformName("  ")
                .build();
        var bindingResult = new MapBindingResult(Map.of(), "newPlatform");
        bindingResult.addError(new FieldError("newPlatform", "platformName", "error"));
        var uriComponentsBuilder = UriComponentsBuilder.fromUriString("http://localhost");
        var exception = assertThrows(BindException.class,
                () -> this.controller.createPlatform(newPlatform, bindingResult, uriComponentsBuilder));
        assertEquals(List.of(new FieldError("newPlatform", "platformName", "error")),
                exception.getAllErrors());
        verifyNoInteractions(this.platformService);
    }

    @Test
    void createPlatformRequestIsInvalidAndBindResultIsBindExceptionReturnsBadRequest() {
        var payload = NewPlatform.of()
                .platformName("  ")
                .build();
        var bindingResult = new BindException(new MapBindingResult(Map.of(), "newPlatform"));
        bindingResult.addError(new FieldError("newPlatform", "platformName", "error"));
        var uriComponentsBuilder = UriComponentsBuilder.fromUriString("http://localhost");
        var exception = assertThrows(BindException.class,
                () -> this.controller.createPlatform(payload, bindingResult, uriComponentsBuilder));
        assertEquals(List.of(new FieldError("newPlatform", "platformName", "error")), exception.getAllErrors());
        verifyNoInteractions(this.platformService);
    }
}