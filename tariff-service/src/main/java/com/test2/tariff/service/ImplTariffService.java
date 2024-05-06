package com.test2.tariff.service;

import com.test2.tariff.entity.Tariff;
import com.test2.tariff.payload.NewTariff;
import com.test2.tariff.payload.TariffDTO;
import com.test2.tariff.payload.UpdateTariff;
import com.test2.tariff.repository.TariffRepository;
import com.test2.tariff.service.kafka.KafkaSendService;
import com.test2.tariff.service.mapper.TariffMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ImplTariffService implements TariffService {
    private static final String TOPIC_TARIFF = "topic.tariff";
    private final KafkaSendService<String, TariffDTO> kafkaSendService;
    private final TariffRepository tariffRepository;
    private final TariffMapper tariffMapper;

    @Override
    @Transactional
    public TariffDTO createTariff(NewTariff newTariff) {
        Tariff tariff = tariffMapper.mapToTariff(newTariff);
        Tariff createTariff = this.tariffRepository.save(tariff);
        TariffDTO tariffDTO = tariffMapper.mapToTariffDTO(createTariff);
        this.kafkaSendService.sendMessage(TOPIC_TARIFF, tariffDTO.getId(), tariffDTO);
        return tariffDTO;

    }

    @Override
    @Transactional
    public void updateTariff(String id, UpdateTariff updateTariff) {
        UUID uuid = tariffMapper.mapToUUID(id);
        this.tariffRepository.findById(uuid)
                .ifPresentOrElse(tariff -> {
                            tariffMapper.mapToTariff(tariff, updateTariff);
                            tariff.incrementVersion();
                            this.kafkaSendService.sendMessage(TOPIC_TARIFF, tariff.getId().toString(), tariffMapper.mapToTariffDTO(tariff));
                        },
                        () -> {
                            throw new NoSuchElementException("tariff_service.update.errors.not.found");
                        });
    }

    @Override
    public Optional<TariffDTO> findTariffById(String id) {
        UUID uuid = tariffMapper.mapToUUID(id);
        return this.tariffRepository.findById(uuid)
                .map(tariffMapper::mapToTariffDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<TariffDTO> findAllTariffs() {
        Iterable<Tariff> tariffs = this.tariffRepository.findAll();
        return StreamSupport.stream(tariffs.spliterator(), false)
                .map(tariffMapper::mapToTariffDTO)
                .toList();
    }

    @Override
    public void deleteTariff(String id) {
        UUID uuid = tariffMapper.mapToUUID(id);
        this.tariffRepository.deleteById(uuid);
    }
}
