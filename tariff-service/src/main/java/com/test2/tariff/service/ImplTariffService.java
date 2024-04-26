package com.test2.tariff.service;

import com.test2.tariff.payload.NewTariff;
import com.test2.tariff.payload.UpdateTariff;
import com.test2.tariff.entity.Tariff;
import com.test2.tariff.repository.TariffRepository;
import com.test2.tariff.service.mapper.TariffMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImplTariffService implements TariffService {
    private final TariffRepository tariffRepository;

    @Override
    @Transactional
    public Tariff createTariff(NewTariff newTariff) {
        Tariff tariff = TariffMapper.mapToTariff(newTariff);
        return this.tariffRepository.save(tariff);
    }

    @Override
    @Transactional
    public void updateTariff(String id, UpdateTariff updateTariff) {
        UUID uuid = TariffMapper.mapToUUID(id);
        this.tariffRepository.findById(uuid)
                .ifPresentOrElse(tariff -> {
                            TariffMapper.mapToTariff(tariff, updateTariff);
                            tariff.incrementVersion();
                        },
                        () -> {
                            throw new NoSuchElementException("tariff_service.update.errors.not.found");
                        });
    }

    @Override
    public Optional<Tariff> findTariffById(String id) {
        UUID uuid = TariffMapper.mapToUUID(id);
        return this.tariffRepository.findById(uuid);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<Tariff> findAllTariffs() {
        return this.tariffRepository.findAll();
    }

    @Override
    public void deleteTariff(String id) {
        UUID uuid = TariffMapper.mapToUUID(id);
        this.tariffRepository.deleteById(uuid);
    }
}
