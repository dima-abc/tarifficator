package com.test2.product.service;

import com.test2.product.entity.Tariff;

public interface TariffService {
    Tariff createTariff(Tariff tariff);

    void updateTariff(Tariff tariff);

    void deleteTariffById(String tariffId);
}
