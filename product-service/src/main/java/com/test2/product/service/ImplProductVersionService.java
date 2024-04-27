package com.test2.product.service;

import com.test2.product.entity.Product;
import com.test2.product.payload.ProductDTO;
import com.test2.product.repository.ProductRevisionRepository;
import com.test2.product.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImplProductVersionService implements ProductVersionService {
    private final ProductRevisionRepository revisionRepository;
    private final AuditReader auditReader;
    private final ProductMapper productMapper;
    private final long SKIP_REVISION = 1L;

    /**
     * Метод получает актуальную версию продукта.
     *
     * @param id String UUID Product
     * @return Optional<Product>
     */
    @Override
    public Optional<ProductDTO> findCurrentVersionProductById(String id) {
        UUID uuid = this.productMapper.mapToUUID(id);
        if (uuid == null) {
            return Optional.empty();
        }
        Optional<Revision<Long, Product>> revisions = this.revisionRepository.findLastChangeRevision(uuid);
        return revisions.map(Revision::getEntity)
                .map(productMapper::mapToProductDTO);
    }

    /**
     * Метод извлекает все предыдущие ревизии.
     *
     * @param id UUID Product
     * @return List<ProductDTO>
     */
    @Override
    public List<ProductDTO> findPreviousVersionsProductById(String id) {
        UUID uuid = this.productMapper.mapToUUID(id);
        if (uuid == null) {
            return List.of();
        }
        Revisions<Long, Product> revisions = this.revisionRepository
                .findRevisions(uuid)
                .reverse();
        return revisions.stream()
                .skip(SKIP_REVISION)
                .map(Revision::getEntity)
                .map(productMapper::mapToProductDTO)
                .toList();
    }

    /**
     * Выборка ревизий за промежуток дат.
     *
     * @param id
     * @param startDate
     * @param endDate
     * @return
     */

    @Override
    public List<ProductDTO> findBetweenDateProductById(String id, LocalDate startDate, LocalDate endDate) {

    //    auditReader.createQuery()
     //           .forRevisionsOfEntity(Product.class, true, true)
     //           .add(AuditEntity.property("id").eq(id))
    //            .add(AuditEntity.revisionProperty("revtstmp").between());

        return List.of();
    }

    @Override
    public Optional<Product> revertProductBeforeVersion(String id) {
        return Optional.empty();
    }
}
