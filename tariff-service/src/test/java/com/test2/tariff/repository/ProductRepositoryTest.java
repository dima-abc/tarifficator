package com.test2.tariff.repository;

import com.test2.tariff.entity.Product;
import com.test2.tariff.entity.Tariff;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql({"/sql/tariff_product_insert.sql"})
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
    UUID tariffId1 = UUID.fromString("ecbfd764-b3cf-4207-a86a-04ca643f7c88");
    UUID tariffId2 = UUID.fromString("df32bd16-82ad-48b4-952f-f561a947593c");
    UUID productId1 = UUID.fromString("73cf2057-c440-49b2-9a4f-94668d53cda0");
    UUID productId2 = UUID.fromString("c94abdb8-4490-4e0f-a7e2-3659b78c336f");
    UUID productId3 = UUID.fromString("26626214-89d9-4080-bf96-148fde927941");
    Tariff tariff1 = Tariff.of().id(tariffId1).build();
    Tariff tariff2 = Tariff.of().id(tariffId2).build();
    Product product1 = new Product(productId1, tariff1, 1);
    Product product2 = new Product(productId2, tariff1, 2);
    Product product3 = new Product(productId3, tariff2, 3);
    @Autowired
    ProductRepository productRepository;

    @Test
    void dependencyNotNull() {
        assertNotNull(productRepository);
    }

    @Test
    void findAllByTariff_then_return_empty_list() {
        Pageable pageable = Pageable.ofSize(1);
        UUID uuid = UUID.randomUUID();
        List<Product> result = this.productRepository.findAllByTariffId(uuid, pageable);
        assertTrue(result.isEmpty());
    }

    @Test
    void findAllByProduct_then_return_list_product_size_one() {
        Pageable pageable = Pageable.ofSize(5);
        List<Product> expect = List.of(product3);
        List<Product> actual = this.productRepository.findAllByTariffId(tariffId2, pageable);
        assertEquals(expect.size(), actual.size());
        assertEquals(expect, actual);
    }

    @Test
    void findAllByProduct_then_return_list_product_size_two() {
        Pageable pageable = Pageable.ofSize(5);
        List<Product> expect = List.of(product1, product2);
        List<Product> actual = this.productRepository.findAllByTariffId(tariffId1, pageable);
        assertEquals(expect.size(), actual.size());
        assertEquals(expect, actual);
    }
}