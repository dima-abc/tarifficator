package com.test2.product.payload;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "of")
public class NewProduct {
    @NotNull(message = "{product_service.create.errors.name_is_null}")
    @Size(min = 3, max = 255, message = "{product_service.create.errors.name_size_is_invalid}")
    private String name;
    @Min(value = 1, message = "{product_service.create.errors.type_value}")
    private int typeProductId;
    @NotNull(message = "{product_service.create.errors.start_date_is_null}")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "{product_service.create.errors.date_format}")
    private String startDate;
    @NotNull(message = "{product_service.create.errors.end_date_is_null}")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "{product_service.create.errors.date_format}")
    private String endDate;
    @NotNull(message = "{product_service.create.errors.description_is_null}")
    @Size(min = 5, max = 255, message = "{product_service.create.errors.description_size_is_invalid}")
    private String description;
    @NotBlank(message = "{product_service.create.errors.tariff_id_is_null}")
    private String tariffId;
    @NotBlank(message = "{product_service.create.errors.author_id_is_null}")
    private String authorId;
}
