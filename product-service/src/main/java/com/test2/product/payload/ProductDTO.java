package com.test2.product.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "of")
public class ProductDTO {
    private String id;
    private String name;
    private int typeProductId;
    private String typeProductName;
    private String startDate;
    private String endDate;
    private String description;
    private String tariffId;
    private long tariffVersion;
    private String authorId;
    private long version;
}
