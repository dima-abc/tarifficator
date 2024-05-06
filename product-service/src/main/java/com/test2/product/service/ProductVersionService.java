package com.test2.product.service;

import com.test2.product.payload.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductVersionService {
    Optional<ProductDTO> findCurrentVersionProductById(String id);

    List<ProductDTO> findPreviousVersionsProductById(String id);

    List<ProductDTO> findBetweenDateProductById(String id, String startPeriod, String endPeriod);

    Optional<ProductDTO> revertProductBeforeVersion(String id);
}
