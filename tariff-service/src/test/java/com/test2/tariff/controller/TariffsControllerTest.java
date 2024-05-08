package com.test2.tariff.controller;

import com.test2.tariff.payload.NewTariff;
import com.test2.tariff.payload.TariffDTO;
import com.test2.tariff.service.TariffService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TariffsControllerTest {
    @Mock
    TariffService tariffService;
    @InjectMocks
    TariffsController tariffsController;

    @Test
    void dependencyNotNull() {
        assertNotNull(tariffService);
        assertNotNull(tariffsController);
    }

    @Test
    void findAllTariff_returns_emptyList() {
        doReturn(List.of()).when(this.tariffService)
                .findAllTariffs();
        Iterable<TariffDTO> result = this.tariffsController.findAllTariff();
        assertEquals(List.of(), result);
    }

    @Test
    void findAllTariff_returns_List_tariffs() {
        String uuid1 = UUID.randomUUID().toString();
        String uuid2 = UUID.randomUUID().toString();
        TariffDTO tariff1 = TariffDTO.of().id(uuid1).build();
        TariffDTO tariff2 = TariffDTO.of().id(uuid2).build();
        doReturn(List.of(tariff1, tariff2)).when(this.tariffService)
                .findAllTariffs();
        Iterable<TariffDTO> result = this.tariffsController.findAllTariff();
        assertEquals(List.of(tariff1, tariff2), result);
    }

    @Test
    void createTariff_request_isValid_returns_CreatedTariff() throws BindException {
        String uuid = UUID.randomUUID().toString();
        NewTariff newTariff = NewTariff.of()
                .name("name").startDate("2020-02-22").endDate("2024-04-08").description("description")
                .rate(11.0D)
                .build();
        TariffDTO tariffDTO = TariffDTO.of().id(uuid).name("name").startDate("2020-02-22")
                .endDate("2024-04-08").description("description").rate(11.0D).build();
        BindingResult bindingResult = new BeanPropertyBindingResult(newTariff, "newTariff");
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString("http://localhost");
        doReturn(tariffDTO).when(this.tariffService).createTariff(newTariff);
        ResponseEntity<?> result = this.tariffsController
                .createTariff(newTariff, bindingResult, uriComponentsBuilder);
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(URI.create("http://localhost/api/v1/tariffs/%s".formatted(uuid)),
                result.getHeaders().getLocation());
        assertEquals(tariffDTO, result.getBody());
        verify(this.tariffService).createTariff(newTariff);
        verifyNoMoreInteractions(this.tariffService);
    }

    @Test
    void createTariff_request_IsInvalid_returns_BadRequest() {
        NewTariff newTariff = NewTariff.of().name(" ").startDate("2020-02-22").endDate("2024-04-08")
                .description("description").rate(11.0D).build();
        BindingResult bindingResult = new BeanPropertyBindingResult(newTariff, "newTariff");
        bindingResult.addError(new FieldError("newTariff", "name", "error"));
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString("http://localhost");
        BindException errors = assertThrows(BindException.class,
                () -> this.tariffsController.createTariff(newTariff, bindingResult, uriComponentsBuilder));
        assertEquals(new FieldError("newTariff", "name", "error"), errors.getFieldError());
        verifyNoInteractions(this.tariffService);
    }
}