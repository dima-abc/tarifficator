package com.test2.client.payload.tariff;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateTariff(
        @Size(min = 3, max = 255, message = "Имя тарифа должно быть от {min} до {max} символов")
        String name,
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "Дата должна быть в формате yyyy-MM-dd")
        String startDate,
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "Дата должна быть в формате yyyy-MM-dd")
        String endDate,
        @Size(min = 5, max = 255, message = "Описание тарифа должно быть от {min} до {max} символов")
        String description,
        Double rate) {
}
