package com.test2.client.payload.tariff;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateTariff(
        @Size(min = 3, max = 255, message = "{tariff_service.update.errors.name_size_is_invalid}")
        String name,
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "{tariff_service.create.errors.date_format}")
        String startDate,
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "{tariff_service.create.errors.date_format}")
        String endDate,
        @Size(min = 5, message = "{tariff_service.update.errors.description_size_is_invalid}")
        String description,
        Double rate) {
}
