package com.test2.product.service.mapper;

import com.test2.product.entity.Product;
import com.test2.product.entity.Tariff;
import com.test2.product.enums.TypeProduct;
import com.test2.product.payload.NewProduct;
import com.test2.product.payload.ProductDTO;
import com.test2.product.payload.TariffDTO;
import com.test2.product.payload.UpdateProduct;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private final ProductMapper productMapper = new ProductMapper();

    @Test
    void mapToProduct_newProduct_then_return_Product() {
        UUID uuid = UUID.randomUUID();
        Product expect = Product.of()
                .name("Product Name")
                .typeProduct(TypeProduct.LOAN)
                .startDate(LocalDate.of(2024, 4, 27))
                .endDate(LocalDate.of(2025, 5, 1))
                .description("Product Description")
                .tariff(new Tariff(uuid, 2L))
                .authorId(uuid)
                .build();
        NewProduct newProduct = NewProduct.of()
                .name("Product Name")
                .typeProductId(1)
                .startDate("2024-04-27")
                .endDate("2025-05-01")
                .description("Product Description")
                .tariffId(uuid.toString())
                .tariffVersion(2L)
                .authorId(uuid.toString())
                .build();
        Product actual = this.productMapper.mapToProduct(newProduct);
        assertEquals(expect, actual);
    }

    @Test
    void mapToProduct_UpdateProduct_then_set_param_Product() {
        UUID uuid = UUID.randomUUID();
        Product product = Product.of()
                .id(uuid)
                .name("Product Name")
                .typeProduct(TypeProduct.LOAN)
                .startDate(LocalDate.of(2024, 4, 27))
                .endDate(LocalDate.of(2025, 5, 1))
                .description("Product Description")
                .tariff(new Tariff(uuid, 2L))
                .authorId(uuid)
                .build();
        Product expect = Product.of()
                .id(uuid)
                .name("new Product Name")
                .typeProduct(TypeProduct.CARD)
                .startDate(LocalDate.of(2024, 4, 27))
                .endDate(LocalDate.of(2025, 5, 1))
                .description("new Product Description")
                .tariff(new Tariff(uuid, 3L))
                .authorId(uuid)
                .build();
        UpdateProduct updateProduct = UpdateProduct.of()
                .name("new Product Name")
                .typeProductId(2)
                .startDate("2024-04-27")
                .endDate("2025-05-01")
                .description("new Product Description")
                .tariffId(uuid.toString())
                .tariffVersion(3L)
                .authorId(uuid.toString())
                .build();
        this.productMapper.mapToProduct(product, updateProduct);
        assertEquals(expect, product);
    }

    @Test
    void mapToProduct_UpdateProduct_param_is_null_param_Product() {
        UUID uuid = UUID.randomUUID();
        Product product = Product.of()
                .id(uuid)
                .name("Product Name")
                .typeProduct(TypeProduct.LOAN)
                .startDate(LocalDate.of(2024, 4, 27))
                .endDate(LocalDate.of(2025, 5, 1))
                .description("Product Description")
                .tariff(new Tariff(uuid, 2L))
                .authorId(uuid)
                .build();
        Product expect = Product.of()
                .id(uuid)
                .name("Product Name")
                .typeProduct(TypeProduct.LOAN)
                .startDate(LocalDate.of(2024, 4, 27))
                .endDate(LocalDate.of(2025, 5, 1))
                .description("Product Description")
                .tariff(new Tariff(uuid, 2L))
                .authorId(uuid)
                .build();
        UpdateProduct updateProduct = new UpdateProduct();
        this.productMapper.mapToProduct(product, updateProduct);
        assertEquals(expect, product);
    }

    @Test
    void mapToProductDTO_return_ProductDTO() {
        UUID uuid = UUID.randomUUID();
        Product product = Product.of()
                .id(uuid)
                .name("Product Name")
                .typeProduct(TypeProduct.CARD)
                .startDate(LocalDate.of(2024, 4, 27))
                .endDate(LocalDate.of(2025, 5, 1))
                .description("Product Description")
                .tariff(new Tariff(uuid, 2L))
                .authorId(uuid)
                .build();
        ProductDTO expect = ProductDTO.of()
                .id(uuid.toString())
                .name("Product Name")
                .typeProductId(TypeProduct.CARD.getId())
                .typeProductName(TypeProduct.CARD.getName())
                .startDate("2024-04-27")
                .endDate("2025-05-01")
                .description("Product Description")
                .tariffId(uuid.toString())
                .tariffVersion(2L)
                .authorId(uuid.toString())
                .build();
        ProductDTO actual = this.productMapper.mapToProductDTO(product);
        assertEquals(expect, actual);
    }

    @Test
    void mapToUUID_set_null_or_empty_or_no_uuid_return_null() {
        UUID actualNull = this.productMapper.mapToUUID(null);
        UUID actualEmpty = this.productMapper.mapToUUID("");
        UUID actualNoFormat = this.productMapper.mapToUUID("abcd");
        assertNull(actualNull);
        assertNull(actualEmpty);
        assertNull(actualNoFormat);
    }

    @Test
    void mapToUUID_string_return_UUID() {
        UUID expect = UUID.randomUUID();
        String target = expect.toString();
        UUID actual = this.productMapper.mapToUUID(target);
        assertEquals(expect, actual);
    }

    @Test
    void mapToDate_set_null_or_empty_or_no_date_return_null() {
        LocalDate actualNull = this.productMapper.mapToDate(null);
        LocalDate actualEmpty = this.productMapper.mapToDate("");
        LocalDate actualNoFormat = this.productMapper.mapToDate("abcd");
        assertNull(actualNull);
        assertNull(actualEmpty);
        assertNull(actualNoFormat);
    }

    @Test
    void mapToDate_string_return_Date() {
        LocalDate expect = LocalDate.of(2024, 4, 27);
        String target = "2024-04-27";
        LocalDate actual = this.productMapper.mapToDate(target);
        assertEquals(expect, actual);
    }

    @Test
    void mapToTypeProduct() {
    }

    @Test
    void mapToTariff_return_Tariff() {
        UUID uuid = UUID.randomUUID();
        Tariff expect = new Tariff(uuid, 2L);
        Tariff actual = this.productMapper.mapToTariff(uuid.toString(), 2L);
        assertEquals(expect, actual);
    }

    @Test
    void mapToTariffDTO_return_TariffDTO() {
        UUID uuid = UUID.randomUUID();
        Tariff tariff = new Tariff(uuid, 2L);
        TariffDTO expect = new TariffDTO(uuid.toString(), 2L);
        TariffDTO actual = this.productMapper.mapToTariffDTO(tariff);
        assertEquals(expect, actual);
    }
}