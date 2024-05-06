package com.test2.tariff.repository;

import com.test2.tariff.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByTariff(UUID tariffId, Pageable pageable);
}
