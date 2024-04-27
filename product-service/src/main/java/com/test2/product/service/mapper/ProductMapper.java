package com.test2.product.service.mapper;

import com.test2.product.entity.Product;
import com.test2.product.entity.Tariff;
import com.test2.product.enums.TypeProduct;
import com.test2.product.payload.NewProduct;
import com.test2.product.payload.ProductDTO;
import com.test2.product.payload.TariffDTO;
import com.test2.product.payload.UpdateProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import java.util.stream.Stream;

@Component
@Slf4j
public class ProductMapper {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_DATE;

    public Product mapToProduct(NewProduct newProduct) {
        TypeProduct typeProduct = mapToTypeProduct(newProduct.getTypeProductId());
        LocalDate startDate = mapToDate(newProduct.getStartDate());
        LocalDate endDate = mapToDate(newProduct.getEndDate());
        Tariff tariff = mapToTariff(newProduct.getTariffId(), newProduct.getTariffVersion());
        UUID authorId = mapToUUID(newProduct.getAuthorId());
        return Product.of()
                .name(newProduct.getName())
                .typeProduct(typeProduct)
                .startDate(startDate)
                .endDate(endDate)
                .description(newProduct.getDescription())
                .tariff(tariff)
                .authorId(authorId)
                .build();
    }

    public void mapToProduct(Product product, UpdateProduct updateProduct) {
        TypeProduct typeProduct = mapToTypeProduct(updateProduct.getTypeProductId());
        LocalDate startDate = mapToDate(updateProduct.getStartDate());
        LocalDate endDate = mapToDate(updateProduct.getEndDate());
        Tariff tariff = mapToTariff(updateProduct.getTariffId(), updateProduct.getTariffVersion());
        UUID authorId = mapToUUID(updateProduct.getAuthorId());
        product.setName(updateProduct.getName() != null ? updateProduct.getName() : product.getName());
        product.setTypeProduct(typeProduct != null ? typeProduct : product.getTypeProduct());
        product.setStartDate(startDate != null ? startDate : product.getStartDate());
        product.setEndDate(endDate != null ? endDate : product.getEndDate());
        product.setDescription(updateProduct.getDescription() != null ? updateProduct.getDescription() : product.getDescription());
        product.setTariff(tariff != null ? tariff : product.getTariff());
        product.setAuthorId(authorId != null ? authorId : product.getAuthorId());
    }

    public ProductDTO mapToProductDTO(Product product) {
        return ProductDTO.of()
                .id(product.getId().toString())
                .name(product.getName())
                .typeProductId(product.getTypeProduct().getId())
                .typeProductName(product.getTypeProduct().getName())
                .startDate(product.getStartDate().toString())
                .endDate(product.getEndDate().toString())
                .description(product.getDescription())
                .tariffId(product.getTariff().getId().toString())
                .tariffVersion(product.getTariff().getVersion())
                .authorId(product.getAuthorId().toString())
                .version(product.getVersion())
                .build();
    }

    public UUID mapToUUID(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            log.error("Invalid UUID: {}", id, e);
            return null;
        }
    }

    public LocalDate mapToDate(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(date, dateFormat);
        } catch (NullPointerException | DateTimeParseException e) {
            log.error("Error while parsing date: {}", date, e);
            return null;
        }
    }

    public TypeProduct mapToTypeProduct(int typeProductId) {
        if (typeProductId < 0) {
            return null;
        }
        return Stream.of(TypeProduct.values())
                .filter(t -> typeProductId == t.getId())
                .findFirst()
                .orElse(null);
    }

    public Tariff mapToTariff(String tariffId, long version) {
        UUID tariffUuid = mapToUUID(tariffId);
        if (tariffUuid == null) {
            return null;
        }
        return new Tariff(tariffUuid, version);
    }

    public TariffDTO mapToTariffDTO(Tariff tariff) {
        return new TariffDTO(tariff.getId().toString(), tariff.getVersion());
    }
}
