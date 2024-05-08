package com.test2.product.service;

import com.test2.product.entity.Product;
import com.test2.product.entity.Tariff;
import com.test2.product.enums.TypeProduct;
import com.test2.product.payload.ProductDTO;
import com.test2.product.repository.ProductRepository;
import com.test2.product.repository.ProductRevisionRepository;
import com.test2.product.service.kafka.KafkaSendService;
import com.test2.product.service.mapper.ProductMapper;
import org.hibernate.envers.DefaultRevisionEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.envers.repository.support.DefaultRevisionMetadata;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.data.history.Revisions;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImplProductVersionServiceTest {
    @Mock
    ProductRepository productRepository;
    @Mock
    ProductRevisionRepository productRevisionRepository;
    @Mock
    KafkaSendService<String, ProductDTO> kafkaSendService;
    @Spy
    ProductMapper productMapper = new ProductMapper();
    @InjectMocks
    ImplProductVersionService implProductVersionService;

    @Test
    void dependencyNotNull() {
        assertNotNull(this.productRepository);
        assertNotNull(this.productRevisionRepository);
        assertNotNull(this.kafkaSendService);
        assertNotNull(this.implProductVersionService);
    }

    @Test
    void findCurrentVersionProductById_uuid_incorrect_return_OptionalEmpty() {
        String productId = "productId";
        Optional<ProductDTO> actual = this.implProductVersionService.findCurrentVersionProductById(productId);
        assertTrue(actual.isEmpty());
    }

    @Test
    void findCurrentVersionProductById_find_revision_empty_then_return_OptionalEmpty() {
        UUID uuid = UUID.randomUUID();
        doReturn(Optional.empty()).when(this.productRevisionRepository)
                .findLastChangeRevision(uuid);
        Optional<ProductDTO> actual = this.implProductVersionService.findCurrentVersionProductById(uuid.toString());
        assertTrue(actual.isEmpty());
    }

    @Test
    void findCurrentVersionProductById_return_product_current_version() {
        UUID tariffId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        Tariff tariff = new Tariff(tariffId, 1L);
        Product product = Product.of().id(productId).name("Product").typeProduct(TypeProduct.LOAN)
                .startDate(LocalDate.of(2020, 4, 4))
                .endDate(LocalDate.of(2025, 5, 5))
                .description("detail product").tariff(tariff)
                .authorId(authorId).version(1L).build();
        ProductDTO expected = ProductDTO.of().id(productId.toString()).name("Product")
                .typeProductId(TypeProduct.LOAN.getId())
                .typeProductName(TypeProduct.LOAN.getName())
                .startDate("2020-04-04").endDate("2025-05-05")
                .description("detail product")
                .tariffId(tariffId.toString())
                .tariffVersion(1L)
                .authorId(authorId.toString())
                .version(1L).build();
        RevisionMetadata<Integer> revisionMetadata = new DefaultRevisionMetadata(new DefaultRevisionEntity());
        Revision<Integer, Product> revision = Revision.of(revisionMetadata, product);
        doReturn(Optional.of(revision)).when(this.productRevisionRepository).findLastChangeRevision(productId);
        Optional<ProductDTO> actual = this.implProductVersionService.findCurrentVersionProductById(productId.toString());
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
        verify(this.productRevisionRepository).findLastChangeRevision(productId);
        verifyNoMoreInteractions(this.productRevisionRepository);
    }

    @Test
    void findPreviousVersionsProductById_uuid_incorrect_return_ListEmpty() {
        String productId = "productId";
        List<ProductDTO> actual = this.implProductVersionService.findPreviousVersionsProductById(productId);
        assertTrue(actual.isEmpty());
    }

    @Test
    void findPreviousVersionsProductById_find_revision_empty_return_ListEmpty() {
        UUID uuid = UUID.randomUUID();
        doReturn(Revisions.of(List.of())).when(this.productRevisionRepository)
                .findRevisions(uuid);
        List<ProductDTO> actual = this.implProductVersionService
                .findPreviousVersionsProductById(uuid.toString());
        assertTrue(actual.isEmpty());
        verify(this.productRevisionRepository).findRevisions(uuid);
        verifyNoMoreInteractions(this.productRevisionRepository);
    }

    @Test
    void findPreviousVersionsProductById_return_product_previous_version() {
        UUID tariffId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        Tariff tariff = new Tariff(tariffId, 1L);
        Product product1 = Product.of().id(productId).name("Product1").typeProduct(TypeProduct.LOAN)
                .startDate(LocalDate.of(2020, 4, 4))
                .endDate(LocalDate.of(2025, 5, 5))
                .description("detail product").tariff(tariff)
                .authorId(authorId).version(1L).build();
        Product product2 = Product.of().id(productId).name("Product2").typeProduct(TypeProduct.CARD)
                .startDate(LocalDate.of(2020, 4, 4))
                .endDate(LocalDate.of(2025, 5, 5))
                .description("detail product").tariff(tariff)
                .authorId(authorId).version(2L).build();
        ProductDTO expected = ProductDTO.of().id(productId.toString()).name("Product1")
                .typeProductId(TypeProduct.LOAN.getId())
                .typeProductName(TypeProduct.LOAN.getName())
                .startDate("2020-04-04").endDate("2025-05-05")
                .description("detail product")
                .tariffId(tariffId.toString())
                .tariffVersion(1L)
                .authorId(authorId.toString())
                .version(1L).build();
        DefaultRevisionEntity revisionEntity1 = new DefaultRevisionEntity();
        revisionEntity1.setId(1);
        DefaultRevisionEntity revisionEntity2 = new DefaultRevisionEntity();
        revisionEntity2.setId(2);
        RevisionMetadata<Integer> revisionMetadata1 = new DefaultRevisionMetadata(revisionEntity1);
        RevisionMetadata<Integer> revisionMetadata2 = new DefaultRevisionMetadata(revisionEntity2);
        Revision<Integer, Product> revision1 = Revision.of(revisionMetadata1, product1);
        Revision<Integer, Product> revision2 = Revision.of(revisionMetadata2, product2);
        doReturn(Revisions.of(List.of(revision2, revision1))).when(this.productRevisionRepository)
                .findRevisions(productId);
        List<ProductDTO> actual = this.implProductVersionService.findPreviousVersionsProductById(productId.toString());
        assertEquals(List.of(expected), actual);
        verify(this.productRevisionRepository).findRevisions(productId);
        verifyNoMoreInteractions(this.productRevisionRepository);
    }
}