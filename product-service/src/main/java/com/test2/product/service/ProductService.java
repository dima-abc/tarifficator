package com.test2.product.service;

import com.test2.product.entity.Product;

import java.time.LocalDate;
import java.util.Optional;

public interface ProductService {
    Product createProduct(Product product);

    Optional<Product> findProductById(String id);

    Optional<Product> findProductByIdBeforeVersion(String id);

    Iterable<Product> findProductByIdBetweenDate(String id, LocalDate startDate, LocalDate endDate);

    void updateProduct(Product product);

    Optional<Product> revertProductBeforeVersion(String id);

    void deleteProduct(String id);

    Iterable<Product> findAllProducts();


}
