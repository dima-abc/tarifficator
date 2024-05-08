package com.test2.product.repository;

import com.test2.product.entity.Product;
import com.test2.product.entity.Tariff;
import com.test2.product.enums.TypeProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql("/sql/product_tariff_insert.sql")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;
    private final UUID tariff1 = UUID.fromString("ab008c85-e2e4-4bf7-894f-c60b99926ae2");
    private final UUID tariff2 = UUID.fromString("13e1895d-2f1d-4766-a2a4-9af3adadfb38");
    private final UUID tariff3 = UUID.fromString("0aab4e94-5564-4ce4-8581-e6d956016844");
    private final UUID authorId = UUID.randomUUID();

    @Test
    void productRepository_is_not_null() {
        assertNotNull(productRepository);
    }

    @Test
    void save_product_is_not_null() {
        Tariff tariff = new Tariff(tariff1, 0);
        Product product = Product.of()
                .name("product")
                .typeProduct(TypeProduct.CARD)
                .startDate(LocalDate.of(2024, 4, 27))
                .endDate(LocalDate.of(2025, 5, 1))
                .description("описание продукта")
                .tariff(tariff)
                .authorId(authorId)
                .build();
        this.productRepository.save(product);
        Optional<Product> productInDb = this.productRepository.findById(product.getId());
        assertTrue(productInDb.isPresent());
        assertEquals(product, productInDb.get());
    }
}