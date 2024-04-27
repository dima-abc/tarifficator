package com.test2.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Audited
@Entity
@Table(name = "tariff", schema = "product_service")
public class Tariff {
    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private UUID id;
    @Column(name = "version")
    private long version;
}
