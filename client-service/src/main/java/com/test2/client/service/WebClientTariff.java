package com.test2.client.service;

import com.test2.client.payload.tariff.NewTariff;
import com.test2.client.payload.tariff.TariffDTO;
import com.test2.client.payload.tariff.UpdateTariff;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class WebClientTariff implements TariffClientService {
    private static final String BASE_URL_TARIFF = "api/v1/tariffs";
    private static final String BASE_URL_TARIFF_ID = "/api/v1/tariffs/{id}";
    private final WebClient webClient;

    @Override
    public Mono<TariffDTO> createTariff(NewTariff newTariff) {
        return this.webClient
                .post()
                .uri(BASE_URL_TARIFF)
                .bodyValue(newTariff)
                .retrieve()
                .bodyToMono(TariffDTO.class);
    }

    @Override
    public Mono<Void> updateTariff(String tariffId, UpdateTariff updateTariff) {
        return this.webClient
                .patch()
                .uri(BASE_URL_TARIFF_ID, tariffId)
                .bodyValue(updateTariff)
                .retrieve()
                .toBodilessEntity()
                .then();
    }

    @Override
    public Mono<Void> deleteTariff(String tariffId) {
        return this.webClient
                .delete()
                .uri(BASE_URL_TARIFF_ID, tariffId)
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}
