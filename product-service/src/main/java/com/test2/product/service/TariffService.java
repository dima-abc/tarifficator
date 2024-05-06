package com.test2.product.service;

import com.test2.product.entity.Tariff;
import com.test2.product.payload.TariffDTO;

import java.util.Optional;

public interface TariffService {
    TariffDTO createTariff(TariffDTO tariff);

    Optional<Tariff> getTariffById(String id);
}
