package com.test2.client.payload.product;

public record ProductDTO(String id,
                         String name,
                         Integer typeProductId,
                         String typeProductName,
                         String startDate,
                         String endDate,
                         String description,
                         String tariffId,
                         Long tariffVersion,
                         String authorId,
                         Long version) {
}
