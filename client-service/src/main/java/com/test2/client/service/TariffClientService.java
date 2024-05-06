package com.test2.client.service;

import com.test2.client.payload.tariff.NewTariff;
import com.test2.client.payload.tariff.TariffDTO;
import com.test2.client.payload.tariff.UpdateTariff;
import reactor.core.publisher.Mono;

public interface TariffClientService {
    Mono<TariffDTO> createTariff(NewTariff newTariff);

    Mono<Void> updateTariff(String tariffId, UpdateTariff updateTariff);

    Mono<Void> deleteTariff(String tariffId);
}
