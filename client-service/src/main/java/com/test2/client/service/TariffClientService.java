package com.test2.client.service;

import com.test2.client.payload.tariff.NewTariff;
import com.test2.client.payload.tariff.TariffDTO;
import com.test2.client.payload.tariff.UpdateTariff;
import reactor.core.publisher.Mono;

public interface TariffClientService {
    Mono<TariffDTO> createTariff(NewTariff newTariff, String token);

    Mono<Void> updateTariff(String tariffId, UpdateTariff updateTariff, String token);

    Mono<Void> deleteTariff(String tariffId, String token);
}
