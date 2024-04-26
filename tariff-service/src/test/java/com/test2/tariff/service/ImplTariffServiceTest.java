package com.test2.tariff.service;

import com.test2.tariff.entity.Tariff;
import com.test2.tariff.payload.NewTariff;
import com.test2.tariff.payload.UpdateTariff;
import com.test2.tariff.repository.TariffRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImplTariffServiceTest {
    @Mock
    TariffRepository tariffRepository;

    @InjectMocks
    ImplTariffService tariffService;

    @Test
    void createTariff_return_Tariff() {
        UUID uuid = UUID.randomUUID();
        NewTariff newTariff = NewTariff.of()
                .name("name")
                .startDate("2024-04-24")
                .endDate("2025-04-25")
                .description("description")
                .rate(10D)
                .build();
        Tariff saved = Tariff.of()
                .name("name")
                .startDate(LocalDate.of(2024, 4, 24))
                .endDate(LocalDate.of(2025, 4, 25))
                .description("description")
                .rate(10D)
                .build();
        Tariff expect = Tariff.of()
                .id(uuid)
                .name("name")
                .startDate(LocalDate.of(2024, 4, 24))
                .endDate(LocalDate.of(2025, 4, 25))
                .description("description")
                .rate(10D)
                .build();
        doReturn(expect).when(this.tariffRepository)
                .save(saved);
        Tariff result = this.tariffService.createTariff(newTariff);
        assertEquals(expect, result);
        verify(this.tariffRepository).save(saved);
        verifyNoMoreInteractions(this.tariffRepository);
    }

    @Test
    void updateTariff_Then_Tariff_update_and_version_increment() {
        UUID uuid = UUID.randomUUID();
        UpdateTariff updateTariff = UpdateTariff.of()
                .name("newName")
                .startDate("2023-04-24")
                .endDate("2026-04-25")
                .description("new_description")
                .rate(20D)
                .build();
        Tariff saved = Tariff.of()
                .id(uuid)
                .name("name")
                .startDate(LocalDate.of(2024, 4, 24))
                .endDate(LocalDate.of(2025, 4, 25))
                .description("description")
                .rate(10D)
                .build();
        doReturn(Optional.of(saved)).when(this.tariffRepository)
                .findById(uuid);
        this.tariffService.updateTariff(uuid.toString(), updateTariff);

        verify(this.tariffRepository).findById(uuid);
        verifyNoMoreInteractions(this.tariffRepository);
    }

    @Test
    void updateTariff_Then_TariffDoesNotExist_ThrowsNoSuchElementException() {
        UUID uuid = UUID.randomUUID();
        UpdateTariff updateTariff = new UpdateTariff();
        assertThrows(NoSuchElementException.class, () -> this.tariffService
                .updateTariff(uuid.toString(), updateTariff));
        verify(this.tariffRepository).findById(uuid);
        verifyNoMoreInteractions(this.tariffRepository);
    }

    @Test
    void findTariff_Return_TariffById() {
        UUID uuid = UUID.randomUUID();
        Tariff expected = Tariff.of()
                .id(uuid)
                .name("name")
                .startDate(LocalDate.of(2024, 4, 24))
                .endDate(LocalDate.of(2025, 4, 25))
                .description("description")
                .rate(10D)
                .build();
        doReturn(Optional.of(expected))
                .when(this.tariffRepository).findById(uuid);
        Optional<Tariff> actual = this.tariffService.findTariffById(uuid.toString());
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
        verify(this.tariffRepository).findById(uuid);
        verifyNoMoreInteractions(this.tariffRepository);
    }

    @Test
    void findTariff_Then_TariffByIdDoesNotExist_OptionalEmpty() {
        UUID uuid = UUID.randomUUID();
        Optional<Tariff> tariff = this.tariffService.findTariffById(uuid.toString());
        assertTrue(tariff.isEmpty());
        verify(this.tariffRepository).findById(uuid);
        verifyNoMoreInteractions(this.tariffRepository);
    }

    @Test
    void findAllTariffs_Return_Empty_List() {
        doReturn(List.of()).when(this.tariffRepository)
                .findAll();
        Iterable<Tariff> result = this.tariffService.findAllTariffs();
        assertEquals(List.of(), result);
        verify(this.tariffRepository).findAll();
        verifyNoMoreInteractions(this.tariffRepository);
    }

    @Test
    void findAllTariffs_Return_List_Tariff() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        Tariff tariff1 = Tariff.of()
                .id(uuid1)
                .build();
        Tariff tariff2 = Tariff.of()
                .id(uuid2)
                .build();
        List<Tariff> expected = List.of(tariff1, tariff2);
        doReturn(expected).when(this.tariffRepository)
                .findAll();
        Iterable<Tariff> result = this.tariffService.findAllTariffs();
        assertEquals(expected, result);
        verify(this.tariffRepository).findAll();
        verifyNoMoreInteractions(this.tariffRepository);
    }

    @Test
    void deleteTariff() {
        UUID uuid = UUID.randomUUID();
        this.tariffService.deleteTariff(uuid.toString());
        verify(this.tariffRepository).deleteById(uuid);
        verifyNoMoreInteractions(this.tariffRepository);
    }
}