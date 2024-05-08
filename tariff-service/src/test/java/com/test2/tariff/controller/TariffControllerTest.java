package com.test2.tariff.controller;

import com.test2.tariff.payload.TariffDTO;
import com.test2.tariff.payload.UpdateTariff;
import com.test2.tariff.service.TariffService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TariffControllerTest {
    @Mock
    TariffService tariffService;

    @Mock
    MessageSource messageSource;

    @InjectMocks
    TariffController tariffController;

    @Test
    void dependencyNotNull() {
        assertNotNull(tariffService);
        assertNotNull(messageSource);
        assertNotNull(tariffController);
    }

    @Test
    void getTariff_return_tariffDTO() {
        UUID tariffId = UUID.randomUUID();
        TariffDTO tariffDTO = TariffDTO.of()
                .id(tariffId.toString())
                .build();
        doReturn(Optional.of(tariffDTO)).when(this.tariffService)
                .findTariffById(tariffId.toString());
        TariffDTO actual = this.tariffController.getTariff(tariffId.toString());
        assertEquals(tariffDTO, actual);
    }

    @Test
    void getTariff_return_tariffNotFound() {
        UUID tariffId = UUID.randomUUID();
        doReturn(Optional.empty()).when(this.tariffService)
                .findTariffById(tariffId.toString());
        String messageError = "Тариф с таким ID не найден";
        assertThrows(NoSuchElementException.class,
                () -> this.tariffController.getTariff(tariffId.toString()),
                messageError);
    }

    @Test
    void findTariff_return_tariffDTO() {
        UUID tariffId = UUID.randomUUID();
        TariffDTO tariffDTO = TariffDTO.of().id(tariffId.toString()).build();
        TariffDTO actual = this.tariffController.findTariff(tariffDTO);
        assertEquals(tariffDTO, actual);
    }

    @Test
    void updateTariff_request_valid_return_noContent() throws BindException {
        UUID tariffId = UUID.randomUUID();
        UpdateTariff updateTariff = UpdateTariff.of()
                .name("Update NAME")
                .build();
        BindingResult bindingResult = new BeanPropertyBindingResult(updateTariff, "updateTariff");
        ResponseEntity<?> result = this.tariffController.updateTariff(tariffId.toString(), updateTariff, bindingResult);
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(this.tariffService).updateTariff(tariffId.toString(), updateTariff);
    }

    @Test
    void updateTariff_request_invalid_return_noContent() {
        UUID tariffId = UUID.randomUUID();
        UpdateTariff updateTariff = UpdateTariff.of()
                .name(" ")
                .build();
        BindingResult bindingResult = new BeanPropertyBindingResult(updateTariff, "updateTariff");
        bindingResult.addError(new FieldError("updateTariff", "name", "error"));
        BindException bindException = assertThrows(BindException.class,
                () -> this.tariffController.updateTariff(tariffId.toString(), updateTariff, bindingResult));
        assertEquals(new FieldError("updateTariff", "name", "error"),
                bindException.getFieldError());
        verifyNoInteractions(this.tariffService);
    }

    @Test
    void deleteTariff_return_noContent() {
        UUID tariffId = UUID.randomUUID();
        ResponseEntity<Void> result = this.tariffController.deleteTariff(tariffId.toString());
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(this.tariffService).deleteTariff(tariffId.toString());
    }

    @Test
    void handelNotFound_return_notFound() {
        String messageError = "error.code";
        NoSuchElementException exception = new NoSuchElementException(messageError);
        Locale locale = Locale.of("ru");
        doReturn("error details").when(this.messageSource)
                .getMessage("error.code", new Object[0], "error.code", Locale.of("ru"));
        ResponseEntity<ProblemDetail> result = this.tariffController.handelNotFound(exception, locale);
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertInstanceOf(ProblemDetail.class, result.getBody());
        assertEquals("error details", result.getBody().getDetail());
        verifyNoInteractions(this.tariffService);
    }
}