package com.test2.tariff.controller;

import com.test2.tariff.payload.NewTariff;
import com.test2.tariff.payload.TariffDTO;
import com.test2.tariff.service.TariffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("api/v1/tariffs")
@RequiredArgsConstructor
public class TariffsController {
    private final TariffService tariffService;

    @GetMapping({"", "/"})
    public Iterable<TariffDTO> findAllTariff() {
        return tariffService.findAllTariffs();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTariff(@Valid @RequestBody NewTariff newTariff,
                                          BindingResult bindingResult,
                                          UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            TariffDTO tariff = this.tariffService.createTariff(newTariff);
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/api/v1/tariffs/{tariffId}")
                            .build(Map.of("tariffId", tariff.getId())))
                    .body(tariff);
        }
    }
}
