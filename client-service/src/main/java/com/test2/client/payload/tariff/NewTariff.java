package com.test2.client.payload.tariff;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewTariff(
        @NotNull(message = "Имя тарифа должно быть указано")
        @Size(min = 3, max = 255, message = "Имя тарифа должно быть от {min} до {max} символов")
        String name,
        @NotNull(message = "Дата начала действия тарифа должна быть указана")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "Дата должна быть в формате yyyy-MM-dd")
        String startDate,
        @NotNull(message = "Дата окончания действия тарифа должна быть указана")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "Дата должна быть в формате yyyy-MM-dd")
        String endDate,
        @NotNull(message = "Описание тарифа должно быть указано")
        @Size(min = 5, max = 255, message = "Описание тарифа должно быть от {min} до {max} символов")
        String description,
        Double rate) {
}
