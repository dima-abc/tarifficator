package com.test2.product.repository;

import com.test2.product.entity.Tariff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TariffRepository extends CrudRepository<Tariff, UUID>, RevisionRepository<Tariff, UUID, Long> {
}
