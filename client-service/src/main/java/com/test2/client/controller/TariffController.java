package com.test2.client.controller;

import com.test2.client.payload.tariff.NewTariff;
import com.test2.client.payload.tariff.UpdateTariff;
import com.test2.client.service.TariffClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/client/tariffs")
@RequiredArgsConstructor
public class TariffController {
    private final TariffClientService tariffService;

    /**
     * Создание нового тарифа
     *
     * @param newTariff  NewTariff
     * @param uriBuilder UriComponentsBuilder
     * @return Mono
     */
    @PostMapping()
    public Mono<ResponseEntity<?>> createTariff(@Valid @RequestBody NewTariff newTariff,
                                                UriComponentsBuilder uriBuilder,
                                                @RequestHeader("Authorization") String accessToken) {
        return tariffService.createTariff(newTariff, accessToken)
                .map(tariffDTO -> ResponseEntity
                        .created(uriBuilder.replacePath("/api/v1/client/tariff/{id}")
                                .build(tariffDTO.id()))
                        .body(tariffDTO));
    }

    @PatchMapping("/{tariffId}")
    public Mono<ResponseEntity<?>> updateTariff(@PathVariable("tariffId") String tariffId,
                                                @Valid @RequestBody UpdateTariff updateTariff,
                                                @RequestHeader("Authorization") String accessToken) {
        return this.tariffService.updateTariff(tariffId, updateTariff, accessToken)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @DeleteMapping("/{tariffId}")
    public Mono<ResponseEntity<Void>> deleteTariff(@PathVariable("tariffId") String tariffId,
                                                   @RequestHeader("Authorization") String accessToken) {
        return this.tariffService.deleteTariff(tariffId, accessToken)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
