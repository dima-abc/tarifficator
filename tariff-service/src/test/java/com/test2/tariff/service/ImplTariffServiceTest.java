package com.test2.tariff.service;

import com.test2.tariff.entity.Tariff;
import com.test2.tariff.payload.NewTariff;
import com.test2.tariff.payload.TariffDTO;
import com.test2.tariff.payload.UpdateTariff;
import com.test2.tariff.repository.TariffRepository;
import com.test2.tariff.service.kafka.KafkaTariffSendService;
import com.test2.tariff.service.mapper.TariffMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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

    @Mock
    KafkaTariffSendService kafkaService;

    @Spy
    TariffMapper tariffMapper = new TariffMapper();

    @InjectMocks
    ImplTariffService tariffService;

    private final String topicTariff = "topic.tariff";

    @Test
    void dependencyNotNull() {
        assertNotNull(tariffService);
        assertNotNull(tariffRepository);
        assertNotNull(kafkaService);
        assertNotNull(tariffMapper);
    }

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
        Tariff returnSaved = Tariff.of()
                .id(uuid)
                .name("name")
                .startDate(LocalDate.of(2024, 4, 24))
                .endDate(LocalDate.of(2025, 4, 25))
                .description("description")
                .rate(10D)
                .build();
        TariffDTO expect = TariffDTO.of()
                .id(uuid.toString())
                .name("name")
                .startDate("2024-04-24")
                .endDate("2025-04-25")
                .description("description")
                .rate(10D)
                .build();
        doReturn(returnSaved).when(this.tariffRepository)
                .save(saved);
        doNothing().when(this.kafkaService)
                .sendMessage(topicTariff, expect.getId(), expect);
        TariffDTO result = this.tariffService.createTariff(newTariff);
        assertEquals(expect, result);
        verify(this.tariffRepository).save(saved);
        verify(this.kafkaService).sendMessage(topicTariff, result.getId(), result);
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
        TariffDTO tariffDTO = TariffDTO.of()
                .id(uuid.toString())
                .name("newName")
                .startDate("2023-04-24")
                .endDate("2026-04-25")
                .description("new_description")
                .rate(20D)
                .version(saved.getVersion() + 1)
                .build();
        doNothing().when(this.kafkaService)
                .sendMessage(topicTariff, tariffDTO.getId(), tariffDTO);
        doReturn(Optional.of(saved)).when(this.tariffRepository)
                .findById(uuid);
        this.tariffService.updateTariff(uuid.toString(), updateTariff);
        verify(this.kafkaService).sendMessage(topicTariff, tariffDTO.getId(), tariffDTO);
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
        Tariff findReturn = Tariff.of()
                .id(uuid)
                .name("name")
                .startDate(LocalDate.of(2024, 4, 24))
                .endDate(LocalDate.of(2025, 4, 25))
                .description("description")
                .rate(10D)
                .build();
        TariffDTO expected = TariffDTO.of()
                .id(uuid.toString())
                .name("name")
                .startDate("2024-04-24")
                .endDate("2025-04-25")
                .description("description")
                .rate(10D)
                .build();
        doReturn(Optional.of(findReturn))
                .when(this.tariffRepository).findById(uuid);
        Optional<TariffDTO> actual = this.tariffService.findTariffById(uuid.toString());
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
        verify(this.tariffRepository).findById(uuid);
        verifyNoMoreInteractions(this.tariffRepository);
    }

    @Test
    void findTariff_Then_TariffByIdDoesNotExist_OptionalEmpty() {
        UUID uuid = UUID.randomUUID();
        Optional<TariffDTO> tariff = this.tariffService.findTariffById(uuid.toString());
        assertTrue(tariff.isEmpty());
        verify(this.tariffRepository).findById(uuid);
        verifyNoMoreInteractions(this.tariffRepository);
    }

    @Test
    void findAllTariffs_Return_Empty_List() {
        doReturn(List.of()).when(this.tariffRepository)
                .findAll();
        Iterable<TariffDTO> result = this.tariffService.findAllTariffs();
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
                .startDate(LocalDate.of(2024, 4, 24))
                .endDate(LocalDate.of(2025, 4, 25))
                .build();
        Tariff tariff2 = Tariff.of()
                .id(uuid2)
                .startDate(LocalDate.of(2024, 4, 24))
                .endDate(LocalDate.of(2025, 4, 25))
                .build();
        TariffDTO tariffDTO1 = TariffDTO.of()
                .id(uuid1.toString())
                .startDate("2024-04-24")
                .endDate("2025-04-25")
                .build();
        TariffDTO tariffDTO2 = TariffDTO.of()
                .id(uuid2.toString())
                .startDate("2024-04-24")
                .endDate("2025-04-25")
                .build();
        List<TariffDTO> expected = List.of(tariffDTO1, tariffDTO2);
        doReturn(List.of(tariff1, tariff2)).when(this.tariffRepository)
                .findAll();
        Iterable<TariffDTO> result = this.tariffService.findAllTariffs();
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