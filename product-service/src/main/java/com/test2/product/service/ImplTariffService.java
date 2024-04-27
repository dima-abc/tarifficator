package com.test2.product.service;

import com.test2.product.entity.Tariff;
import com.test2.product.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImplTariffService implements TariffService {
    private final TariffRepository tariffRepository;

    @Override
    public Tariff createTariff(Tariff tariff) {
        return this.tariffRepository.save(tariff);
    }

    @Override
    public void updateTariff(Tariff tariff) {

    }

    @Override
    public void deleteTariffById(String tariffId) {

    }
}
