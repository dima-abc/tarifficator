package com.test2.product.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class TypeProductConverter implements AttributeConverter<TypeProduct, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TypeProduct typeProduct) {
        if (typeProduct == null) {
            return null;
        }
        return typeProduct.getId();
    }

    @Override
    public TypeProduct convertToEntityAttribute(Integer typeCode) {
        if (typeCode == null) {
            return null;
        }
        return Stream.of(TypeProduct.values())
                .filter(c -> typeCode == c.getId())
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
