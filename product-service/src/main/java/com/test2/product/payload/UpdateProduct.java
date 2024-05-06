package com.test2.product.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "of")
public class UpdateProduct {
    @Size(min = 3, max = 255, message = "{product_service.create.errors.name_size_is_invalid}")
    private String name;
    @Min(value = 1, message = "{product_service.create.errors.type_value}")
    private int typeProductId;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "{product_service.create.errors.date_format}")
    private String startDate;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "{product_service.create.errors.date_format}")
    private String endDate;
    @Size(min = 5, message = "{product_service.create.errors.description_size_is_invalid}")
    private String description;
    private String tariffId;
    private String authorId;
}
