package com.test2.tariff.repository;

import com.test2.tariff.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, UUID>, RevisionRepository<Tariff, UUID, Long> {
}
