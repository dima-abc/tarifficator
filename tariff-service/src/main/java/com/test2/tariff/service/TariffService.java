package com.test2.tariff.service;

import com.test2.tariff.payload.NewTariff;
import com.test2.tariff.payload.TariffDTO;
import com.test2.tariff.payload.UpdateTariff;

import java.util.Optional;

public interface TariffService {
    TariffDTO createTariff(NewTariff newTariff);

    void updateTariff(String id, UpdateTariff updateTariff);

    Optional<TariffDTO> findTariffById(String id);

    Iterable<TariffDTO> findAllTariffs();

    void deleteTariff(String id);
}
