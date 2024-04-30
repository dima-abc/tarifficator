package com.test2.product.service;

import com.test2.product.entity.Tariff;
import com.test2.product.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImplTariffService implements TariffService {
    private final TariffRepository tariffRepository;

    @Transactional
    @Override
    public Tariff createTariff(Tariff tariff) {
        return this.tariffRepository.save(tariff);
    }

    @Transactional
    @Override
    public void updateTariff(Tariff tariff) {
        this.tariffRepository.save(tariff);
    }

    @Override
    public void deleteTariffById(String tariffId) {
        UUID uuid = UUID.fromString(tariffId);
        this.tariffRepository.deleteById(uuid);
    }
}
