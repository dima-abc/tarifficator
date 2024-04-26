package com.test2.tariff.service.mapper;

import com.test2.tariff.payload.NewTariff;
import com.test2.tariff.payload.UpdateTariff;
import com.test2.tariff.entity.Tariff;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Slf4j
public class TariffMapper {
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_DATE;

    public static Tariff mapToTariff(NewTariff newTariff) {
        return Tariff.of()
                .name(newTariff.getName())
                .startDate(mapToDate(newTariff.getStartDate()))
                .endDate(mapToDate(newTariff.getEndDate()))
                .description(newTariff.getDescription())
                .rate(newTariff.getRate())
                .build();
    }

    public static void mapToTariff(Tariff tariff, UpdateTariff updateTariff) {
        LocalDate startDate = mapToDate(updateTariff.getStartDate());
        LocalDate endDate = mapToDate(updateTariff.getEndDate());
        tariff.setName(
                updateTariff.getName() != null ? updateTariff.getName() : tariff.getName());
        tariff.setStartDate(
                startDate != null ? startDate : tariff.getStartDate());
        tariff.setEndDate(
                endDate != null ? endDate : tariff.getEndDate());
        tariff.setDescription(
                updateTariff.getDescription() != null ? updateTariff.getDescription() : tariff.getDescription());
        tariff.setRate(
                updateTariff.getRate() != null ? updateTariff.getRate() : tariff.getRate());
    }


    public static UUID mapToUUID(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            log.error("Invalid UUID: {}", id, e);
            return null;
        }
    }

    public static LocalDate mapToDate(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(date, dateFormat);
        } catch (NullPointerException | DateTimeParseException e) {
            log.error("Error while parsing date: {}", date, e);
            return null;
        }
    }
}
