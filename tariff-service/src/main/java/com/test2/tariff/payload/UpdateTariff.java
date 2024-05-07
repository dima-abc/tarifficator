package com.test2.tariff.payload;

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
public class UpdateTariff {
    @Size(min = 3, max = 255, message = "{tariff_service.update.errors.name_size_is_invalid}")
    private String name;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "{tariff_service.create.errors.date_format}")
    private String startDate;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "{tariff_service.create.errors.date_format}")
    private String endDate;
    @Size(min = 5, max = 255, message = "{tariff_service.update.errors.description_size_is_invalid}")
    private String description;
    private Double rate;
}
