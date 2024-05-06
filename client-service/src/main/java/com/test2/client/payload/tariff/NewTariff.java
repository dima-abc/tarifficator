package com.test2.client.payload.tariff;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewTariff(
        @NotNull(message = "{tariff-service.create.errors.name_is_null}")
        @Size(min = 3, max = 255, message = "{tariff-service.create.errors.name_size_is_invalid}")
        String name,
        @NotNull(message = "{tariff_service.create.errors.start_date_is_null}")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "{tariff_service.create.errors.date_format}")
        String startDate,
        @NotNull(message = "{tariff_service.create.errors.end_date_is_null}")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "{tariff_service.create.errors.date_format}")
        String endDate,
        @NotNull(message = "{tariff_service.create.errors.description_is_null}")
        @Size(min = 5, message = "{tariff_service.create.errors.description_size_is_invalid}")
        String description,
        Double rate) {
}
