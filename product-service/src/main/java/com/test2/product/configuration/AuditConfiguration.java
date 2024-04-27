package com.test2.product.configuration;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AuditConfiguration {
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public AuditReader getAuditReader() {
        return AuditReaderFactory
                .get(entityManagerFactory.createEntityManager());
    }
}
