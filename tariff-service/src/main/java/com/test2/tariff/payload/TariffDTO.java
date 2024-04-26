package com.test2.tariff.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "of")
public class TariffDTO {
    private String id;
    private String name;
    private String startDate;
    private String endDate;
    private String description;
    private Double rate;
    private long version;
}
