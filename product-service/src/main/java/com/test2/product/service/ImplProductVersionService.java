package com.test2.product.service;

import com.test2.product.entity.Product;
import com.test2.product.payload.ProductDTO;
import com.test2.product.repository.ProductRepository;
import com.test2.product.repository.ProductRevisionRepository;
import com.test2.product.service.kafka.KafkaSendService;
import com.test2.product.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImplProductVersionService implements ProductVersionService {
    private final ProductRevisionRepository revisionRepository;
    private final ProductRepository productRepository;
    private final AuditReader auditReader;
    private final KafkaSendService<String, ProductDTO> kafkaSendService;
    private static final String TOPIC_PRODUCT = "topic.product";
    private final ProductMapper productMapper;

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
     * Метод извлекает все предыдущие ревизии пропускает текущую.
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
        long skipRevision = 1L;
        return revisions.stream()
                .skip(skipRevision)
                .map(Revision::getEntity)
                .map(productMapper::mapToProductDTO)
                .toList();
    }

    /**
     * Выборка ревизий за промежуток дат.
     *
     * @param id          UUID Product
     * @param startPeriod Start period yyyy-mm-dd HH:mm:ss
     * @param endPeriod   End period yyyy-mm-dd HH:mm:ss
     * @return List
     */
    @Override
    public List<ProductDTO> findBetweenDateProductById(String id, String startPeriod, String endPeriod) {
        UUID uuid = this.productMapper.mapToUUID(id);
        LocalDateTime startDateTime = this.productMapper.mapToDateTime(startPeriod);
        LocalDateTime endDateTime = this.productMapper.mapToDateTime(endPeriod);
        long startTimeStamp = productMapper.dateTimeToTimestamp(startDateTime);
        long endTimeStamp = productMapper.dateTimeToTimestamp(endDateTime);
        AuditQuery auditQuery = auditReader.createQuery()
                .forRevisionsOfEntity(Product.class, true, true)
                .add(AuditEntity.property("id").eq(uuid))
                .add(AuditEntity.revisionProperty("timestamp").between(startTimeStamp, endTimeStamp));
        List<Product> products = auditQuery.getResultList();
        return products.stream()
                .map(productMapper::mapToProductDTO)
                .toList();
    }

    /**
     * Откат на предыдущую версию продукта.
     *
     * @param id UUID Product
     * @return Optional
     */
    @Transactional
    @Override
    public Optional<ProductDTO> revertProductBeforeVersion(String id) {
        UUID uuid = this.productMapper.mapToUUID(id);
        Revision<Long, Product> revisions = this.revisionRepository.findLastChangeRevision(uuid)
                .orElseThrow(() -> new NoSuchElementException("product_service.errors.revision.not.found"));
        Product product = revisions.getEntity();
        long versionRevert = product.getVersion() - 1;
        AuditQuery auditQuery = auditReader.createQuery()
                .forRevisionsOfEntity(Product.class, true, true)
                .add(AuditEntity.property("id").eq(uuid))
                .add(AuditEntity.property("version").eq(versionRevert));
        Optional<Product> productBefore = auditQuery.getResultList().stream().findAny();
        Optional<Product> productRevert = Optional.empty();
        if (productBefore.isPresent()) {
            productRevert = Optional.of(this.productRepository.save(productBefore.get()));
            productRevert.ifPresent(
                    p -> this.kafkaSendService
                            .sendMessage(TOPIC_PRODUCT,
                                    p.getId().toString(),
                                    productMapper.mapToProductDTO(p)));
        }
        return productRevert
                .map(productMapper::mapToProductDTO);
    }
}
