package com.test2.tariff.payload;

import jakarta.validation.constraints.NotNull;
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
public class NewTariff {
    @NotNull(message = "{tariff-service.create.errors.name_is_null}")
    @Size(min = 3, max = 255, message = "{tariff-service.create.errors.name_size_is_invalid}")
    private String name;
    @NotNull(message = "{tariff_service.create.errors.start_date_is_null}")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "{tariff_service.create.errors.date_format}")
    private String startDate;
    @NotNull(message = "{tariff_service.create.errors.end_date_is_null}")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "{tariff_service.create.errors.date_format}")
    private String endDate;
    @NotNull(message = "{tariff_service.create.errors.description_is_null}")
    @Size(min = 5, max = 255, message = "{tariff_service.create.errors.description_size_is_invalid}")
    private String description;
    private Double rate;
}
