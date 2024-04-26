package com.test2.product.service;

import com.test2.product.entity.Product;

import java.time.LocalDate;
import java.util.Optional;

public class ImplProductService implements ProductService{
    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Optional<Product> findProductById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> findProductByIdBeforeVersion(String id) {
        return Optional.empty();
    }

    @Override
    public Iterable<Product> findProductByIdBetweenDate(String id, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public Optional<Product> revertProductBeforeVersion(String id) {
        return Optional.empty();
    }

    @Override
    public void deleteProduct(String id) {

    }

    @Override
    public Iterable<Product> findAllProducts() {
        return null;
    }
}
