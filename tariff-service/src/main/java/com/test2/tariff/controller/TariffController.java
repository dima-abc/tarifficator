package com.test2.tariff.controller;

import com.test2.tariff.entity.Tariff;
import com.test2.tariff.payload.UpdateTariff;
import com.test2.tariff.service.ImplTariffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/tariffs/{tariffId}")
@RequiredArgsConstructor
public class TariffController {
    private final ImplTariffService tariffService;
    private final MessageSource messageSource;

    @ModelAttribute("tariff")
    public Tariff getTariff(@PathVariable("tariffId") String tariffId) {
        return this.tariffService.findTariffById(tariffId)
                .orElseThrow(() -> new NoSuchElementException("tariff_service.errors.not.found"));
    }

    @GetMapping
    public Tariff findTariff(@ModelAttribute("tariff") Tariff tariff) {
        return tariff;
    }

    @PatchMapping
    public ResponseEntity<?> updateTariff(@PathVariable("tariffId") String tariffId,
                                          @Valid @RequestBody UpdateTariff updateTariff,
                                          BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            this.tariffService.updateTariff(tariffId, updateTariff);
            return ResponseEntity.noContent()
                    .build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTariff(@PathVariable("tariffId") String tariffId) {
        this.tariffService.deleteTariff(tariffId);
        return ResponseEntity.noContent()
                .build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handelNotFound(NoSuchElementException e,
                                                        Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        Objects.requireNonNull(this.messageSource.getMessage(e.getMessage(), new Object[0],
                                e.getMessage(), locale))));
    }
}
