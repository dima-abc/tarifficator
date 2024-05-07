package com.test2.client.payload.product;

import jakarta.validation.constraints.*;

public record NewProduct(
        @NotNull(message = "Имя продукта должно быть указано")
        @Size(min = 3, max = 255, message = "{product_service.create.errors.name_size_is_invalid}")
        String name,
        @Min(value = 1, message = "Тип продукта должен быть 1=кредит, 2=карта")
        @Max(value = 2, message = "Тип продукта должен быть 1=кредит, 2=карта")
        Integer typeProductId,
        @NotNull(message = "Дата начала действия продукта должна быть указана")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "Дата должна быть в формате yyyy-MM-dd")
        String startDate,
        @NotNull(message = "Дата окончания действия продукта должна быть указана")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "Дата должна быть в формате yyyy-MM-dd")
        String endDate,
        @NotNull(message = "Описание продукта должно быть указано")
        @Size(min = 5, max = 255, message = "Имя продукта должно быть от {min} до {max} символов")
        String description,
        @NotBlank(message = "UUID тарифа должно быть указано")
        String tariffId,
        @NotBlank(message = "UUID пользователя должно быть указано")
        String authorId) {
}
