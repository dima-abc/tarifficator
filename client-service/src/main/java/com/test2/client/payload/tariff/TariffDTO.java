package com.test2.client.payload.tariff;

public record TariffDTO(String id,
                        String name,
                        String startDate,
                        String endDate,
                        String description,
                        Double rate,
                        Long version) {
}
