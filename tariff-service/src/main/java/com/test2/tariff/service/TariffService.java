package com.test2.tariff.service;

import com.test2.tariff.payload.NewTariff;
import com.test2.tariff.payload.UpdateTariff;
import com.test2.tariff.entity.Tariff;

import java.util.Optional;

public interface TariffService {
    Tariff createTariff(NewTariff newTariff);

    void updateTariff(String id, UpdateTariff updateTariff);

    Optional<Tariff> findTariffById(String id);

    Iterable<Tariff> findAllTariffs();

    void deleteTariff(String id);
}
