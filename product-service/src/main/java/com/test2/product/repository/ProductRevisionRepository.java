package com.test2.product.repository;

import com.test2.product.entity.Product;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.UUID;

public interface ProductRevisionRepository extends RevisionRepository<Product, UUID, Long> {
}
