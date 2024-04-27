package com.test2.product.service;

import com.test2.product.entity.Product;
import com.test2.product.payload.ProductDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductVersionService {
    Optional<ProductDTO> findCurrentVersionProductById(String id);

    List<ProductDTO> findPreviousVersionsProductById(String id);

    List<ProductDTO> findBetweenDateProductById(String id, LocalDate startDate, LocalDate endDate);

    Optional<Product> revertProductBeforeVersion(String id);
}
