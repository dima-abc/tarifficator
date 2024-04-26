package com.test2.product.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "of")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private TypeProduct typeProduct;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Tariff tariff;
    private UUID authorId;
    private long version;

}
