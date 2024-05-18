package com.test2.client.controller;

import com.test2.client.payload.tariff.NewTariff;
import com.test2.client.payload.tariff.TariffDTO;
import com.test2.client.payload.tariff.UpdateTariff;
import com.test2.client.service.TariffClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TariffControllerTest {
    @Mock
    TariffClientService tariffClientService;

    @InjectMocks
    TariffController tariffController;

    @Test
    void dependencyNotNull() {
        assertNotNull(this.tariffClientService);
        assertNotNull(this.tariffController);
    }

    @Test
    void createTariff_RequestIsValidThenReturnTariff() {
        UUID uuid = UUID.randomUUID();
        TariffDTO tariffDTO = new TariffDTO(
                uuid.toString(),
                "Tariff name",
                "2024-02-15",
                "2026-02-15",
                "Description tariff",
                25.0D,
                1L);
        NewTariff newTariff = new NewTariff(
                "Tariff name",
                "2024-02-15",
                "2026-02-15",
                "Description tariff",
                25.0D);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString("http://localhost");
        doReturn(Mono.just(tariffDTO)).when(this.tariffClientService)
                .createTariff(newTariff, "token");
        StepVerifier.create(this.tariffController.createTariff(newTariff, uriComponentsBuilder, "token"))
                .expectNext(ResponseEntity
                        .created(URI.create("http://localhost/api/v1/client/tariff/" + uuid))
                        .body(tariffDTO))
                .verifyComplete();
        verify(this.tariffClientService)
                .createTariff(newTariff, "token");
        verifyNoMoreInteractions(this.tariffClientService);

    }

    @Test
    void updateTariff_RequestIsValid_ReturnNoContent() {
        UUID uuid = UUID.randomUUID();
        UpdateTariff updateTariff = new UpdateTariff(
                "Tariff name",
                "2024-02-15",
                "2026-02-15",
                "Description tariff",
                25.0D);
        doReturn(Mono.empty()).when(this.tariffClientService)
                .updateTariff(uuid.toString(), updateTariff, "token");
        StepVerifier.create(this.tariffController.updateTariff(uuid.toString(), updateTariff, "token"))
                .expectNext(ResponseEntity.noContent().build())
                .verifyComplete();
        verify(this.tariffClientService).updateTariff(uuid.toString(), updateTariff, "token");
        verifyNoMoreInteractions(this.tariffClientService);
    }

    @Test
    void deleteTariff_return_noContent() {
        UUID uuid = UUID.randomUUID();
        doReturn(Mono.empty()).when(this.tariffClientService).deleteTariff(uuid.toString(), "token");
        StepVerifier.create(this.tariffController.deleteTariff(uuid.toString(), "token"))
                .expectNext(ResponseEntity.noContent().build())
                .verifyComplete();
        verify(this.tariffClientService).deleteTariff(uuid.toString(), "token");
        verifyNoMoreInteractions(this.tariffClientService);
    }
}