package com.test2.client.payload.product;

public record NewProduct(String name,
                         Integer typeProductId,
                         String startDate,
                         String endDate,
                         String description,
                         String tariffId,
                         String authorId) {
}
