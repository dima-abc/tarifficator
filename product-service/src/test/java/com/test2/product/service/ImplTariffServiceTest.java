package com.test2.product.service;

import com.test2.product.entity.Tariff;
import com.test2.product.payload.TariffDTO;
import com.test2.product.repository.TariffRepository;
import com.test2.product.service.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImplTariffServiceTest {
    @Mock
    TariffRepository tariffRepository;
    @Spy
    ProductMapper productMapper = new ProductMapper();
    @InjectMocks
    ImplTariffService implTariffService;

    @Test
    void dependencyNotNull() {
        assertNotNull(this.tariffRepository);
        assertNotNull(this.productMapper);
        assertNotNull(this.implTariffService);
    }

    @Test
    void createTariff_returns_TariffDTO() {
        UUID uuid = UUID.randomUUID();
        Tariff tariff = new Tariff(uuid, 1L);
        doReturn(tariff).when(this.tariffRepository)
                .save(tariff);
        TariffDTO newTariffDTO = new TariffDTO(uuid.toString(), 1L);
        TariffDTO actual = this.implTariffService.createTariff(newTariffDTO);
        assertEquals(newTariffDTO, actual);
        verify(this.tariffRepository).save(tariff);
        verifyNoMoreInteractions(this.tariffRepository);
    }

    @Test
    void getTariffById_returns_Optional_empty() {
        UUID uuid = UUID.randomUUID();
        doReturn(Optional.empty()).when(this.tariffRepository)
                .findById(uuid);
        Optional<Tariff> actual = this.implTariffService.getTariffById(uuid.toString());
        assertTrue(actual.isEmpty());
        verify(this.tariffRepository).findById(uuid);
        verifyNoMoreInteractions(this.tariffRepository);
    }

    @Test
    void getTariffById_returns_Optional_Tariff() {
        UUID uuid = UUID.randomUUID();
        Tariff tariff = new Tariff(uuid, 1L);
        doReturn(Optional.of(tariff)).when(this.tariffRepository)
                .findById(uuid);
        Optional<Tariff> actual = this.implTariffService.getTariffById(uuid.toString());
        assertTrue(actual.isPresent());
        assertEquals(tariff, actual.get());
        verify(this.tariffRepository).findById(uuid);
        verifyNoMoreInteractions(this.tariffRepository);
    }
}