package com.test2.product.service;

import com.test2.product.entity.Product;
import com.test2.product.entity.Tariff;
import com.test2.product.enums.TypeProduct;
import com.test2.product.payload.NewProduct;
import com.test2.product.payload.ProductDTO;
import com.test2.product.payload.UpdateProduct;
import com.test2.product.repository.ProductRepository;
import com.test2.product.service.kafka.KafkaSendService;
import com.test2.product.service.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImplProductServiceTest {
    @Mock
    KafkaSendService<String, ProductDTO> kafkaSendService;
    @Mock
    ProductRepository productRepository;
    @Mock
    TariffService tariffService;
    @Spy
    ProductMapper productMapper = new ProductMapper();
    @InjectMocks
    ImplProductService implProductService;
    private final String TOPIC_PRODUCT = "topic.product";

    @Test
    void dependencyNotNull() {
        assertNotNull(this.kafkaSendService);
        assertNotNull(this.productRepository);
        assertNotNull(this.tariffService);
        assertNotNull(this.productMapper);
        assertNotNull(this.implProductService);
    }

    @Test
    void createProduct_return_created_product() {
        UUID tariffId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        NewProduct newProduct = NewProduct.of().name("Product").typeProductId(1).startDate("2020-04-04")
                .endDate("2025-05-05").description("detail product").tariffId(tariffId.toString())
                .authorId(authorId.toString()).build();
        Tariff tariff = new Tariff(tariffId, 1L);
        Product savedProduct = Product.of().name("Product").typeProduct(TypeProduct.LOAN)
                .startDate(LocalDate.of(2020, 4, 4)).endDate(LocalDate.of(2025, 5, 5))
                .description("detail product").tariff(tariff).authorId(authorId).version(1L).build();
        Product product = Product.of().id(productId).name("Product").typeProduct(TypeProduct.LOAN)
                .startDate(LocalDate.of(2020, 4, 4)).endDate(LocalDate.of(2025, 5, 5))
                .description("detail product").tariff(tariff).authorId(authorId).version(1L).build();
        ProductDTO expect = ProductDTO.of().id(productId.toString()).name("Product")
                .typeProductId(TypeProduct.LOAN.getId()).typeProductName(TypeProduct.LOAN.getName())
                .startDate("2020-04-04").endDate("2025-05-05").description("detail product")
                .tariffId(tariffId.toString()).tariffVersion(1L).authorId(authorId.toString()).version(1L).build();
        doReturn(Optional.of(tariff)).when(this.tariffService).getTariffById(tariffId.toString());
        doReturn(product).when(this.productRepository).save(savedProduct);
        doNothing().when(this.kafkaSendService)
                .sendMessage(TOPIC_PRODUCT, productId.toString(), expect);
        ProductDTO actual = this.implProductService.createProduct(newProduct);
        assertEquals(expect, actual);
        verify(this.tariffService).getTariffById(tariffId.toString());
        verify(this.productRepository).save(savedProduct);
        verify(this.kafkaSendService).sendMessage(TOPIC_PRODUCT, productId.toString(), expect);
        verifyNoMoreInteractions(this.tariffService, this.productRepository, this.kafkaSendService);
    }

    @Test
    void createProduct_return_created_product_when_tariffNotFound_exception() {
        UUID tariffId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        NewProduct newProduct = NewProduct.of().name("Product").typeProductId(1).startDate("2020-04-04")
                .endDate("2025-05-05").description("detail product").tariffId(tariffId.toString())
                .authorId(authorId.toString()).build();
        doReturn(Optional.empty()).when(this.tariffService).getTariffById(tariffId.toString());
        String errorMessage = "Тариф не найден.";
        assertThrows(NoSuchElementException.class,
                () -> this.implProductService.createProduct(newProduct),
                errorMessage);
    }

    @Test
    void updateProduct_return_updated_product_notFound_exception() {
        UUID tariffId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        UpdateProduct updateProduct = UpdateProduct.of().name("Product").typeProductId(1).startDate("2020-04-04")
                .endDate("2025-05-05").description("detail product").tariffId(tariffId.toString())
                .authorId(authorId.toString()).build();
        doReturn(Optional.empty()).when(this.tariffService).getTariffById(tariffId.toString());
        doReturn(Optional.empty()).when(this.productRepository).findById(productId);
        String errorMessage = "Продукт не найден для обновления";
        assertThrows(NoSuchElementException.class,
                () -> this.implProductService.updateProduct(productId.toString(), updateProduct),
                errorMessage);
    }

    @Test
    void updateProduct_update_product_completed() {
        UUID tariffId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        Tariff tariff = new Tariff(tariffId, 1L);
        UpdateProduct updateProduct = UpdateProduct.of().name("update name").typeProductId(2)
                .startDate("2020-04-04").endDate("2025-05-05")
                .description("update detail product").tariffId(tariffId.toString())
                .authorId(authorId.toString()).build();
        Product product = Product.of().id(productId).name("Product").typeProduct(TypeProduct.LOAN)
                .startDate(LocalDate.of(2020, 4, 4))
                .endDate(LocalDate.of(2025, 5, 5))
                .description("detail product").tariff(tariff).authorId(authorId).version(1L).build();
        ProductDTO productDTO = ProductDTO.of().id(productId.toString()).name("update name")
                .typeProductId(TypeProduct.CARD.getId()).typeProductName(TypeProduct.CARD.getName())
                .startDate("2020-04-04").endDate("2025-05-05")
                .description("update detail product").tariffId(tariffId.toString()).tariffVersion(1L)
                .authorId(authorId.toString()).version(2L).build();
        doReturn(Optional.of(tariff)).when(this.tariffService).getTariffById(tariffId.toString());
        doNothing().when(this.kafkaSendService).sendMessage(TOPIC_PRODUCT, productId.toString(), productDTO);
        doReturn(Optional.of(product)).when(this.productRepository).findById(productId);
        this.implProductService.updateProduct(productId.toString(), updateProduct);
        verify(this.tariffService).getTariffById(tariffId.toString());
        verify(this.productRepository).findById(productId);
        verify(this.kafkaSendService).sendMessage(TOPIC_PRODUCT, productId.toString(), productDTO);
        verifyNoMoreInteractions(this.tariffService, this.productRepository, this.kafkaSendService);
    }

    @Test
    void findProductById_return_optional_empty() {
        UUID productId = UUID.randomUUID();
        doReturn(Optional.empty()).when(this.productRepository).findById(productId);
        Optional<ProductDTO> actual = this.implProductService.findProductById(productId.toString());
        assertTrue(actual.isEmpty());
        verify(this.productRepository).findById(productId);
        verifyNoMoreInteractions(this.productRepository);
    }

    @Test
    void findProductById_return_optional_ProductDTO() {
        UUID tariffId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        Tariff tariff = new Tariff(tariffId, 1L);
        ProductDTO expected = ProductDTO.of().id(productId.toString()).name("Product")
                .typeProductId(TypeProduct.LOAN.getId())
                .typeProductName(TypeProduct.LOAN.getName())
                .startDate("2020-04-04")
                .endDate("2025-05-05")
                .description("detail product").tariffId(tariffId.toString()).tariffVersion(1L)
                .authorId(authorId.toString()).version(1L).build();
        Product product = Product.of().id(productId).name("Product").typeProduct(TypeProduct.LOAN)
                .startDate(LocalDate.of(2020, 4, 4))
                .endDate(LocalDate.of(2025, 5, 5))
                .description("detail product").tariff(tariff).authorId(authorId).version(1L).build();
        doReturn(Optional.of(product)).when(this.productRepository).findById(productId);
        Optional<ProductDTO> actual = this.implProductService.findProductById(productId.toString());
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
        verify(this.productRepository).findById(productId);
        verifyNoMoreInteractions(this.productRepository);
    }

    @Test
    void deleteProductById_Delete_Product() {
        UUID productId = UUID.randomUUID();
        doNothing().when(this.productRepository).deleteById(productId);
        this.implProductService.deleteProductById(productId.toString());
        verify(this.productRepository).deleteById(productId);
        verifyNoMoreInteractions(this.productRepository);
    }

    @Test
    void findAllProducts_return_emptyList() {
        doReturn(List.of()).when(this.productRepository).findAll();
        Iterable<ProductDTO> actual = this.implProductService.findAllProducts();
        assertFalse(actual.iterator().hasNext());
        verify(this.productRepository).findAll();
        verifyNoMoreInteractions(this.productRepository);
    }

    @Test
    void findAllProducts_return_ProductDTOList() {
        UUID tariffId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        Tariff tariff = new Tariff(tariffId, 1L);
        Product product = Product.of().id(productId).name("Product").typeProduct(TypeProduct.LOAN)
                .startDate(LocalDate.of(2020, 4, 4))
                .endDate(LocalDate.of(2025, 5, 5))
                .description("detail product").tariff(tariff)
                .authorId(authorId).version(1L).build();
        ProductDTO productDTO = ProductDTO.of().id(productId.toString()).name("Product")
                .typeProductId(TypeProduct.LOAN.getId())
                .typeProductName(TypeProduct.LOAN.getName())
                .startDate("2020-04-04").endDate("2025-05-05")
                .description("detail product")
                .tariffId(tariffId.toString())
                .tariffVersion(1L)
                .authorId(authorId.toString())
                .version(1L).build();
        doReturn(List.of(product)).when(this.productRepository).findAll();
        Iterable<ProductDTO> actual = this.implProductService.findAllProducts();
        assertTrue(actual.iterator().hasNext());
        assertEquals(productDTO, actual.iterator().next());
        verify(this.productRepository).findAll();
        verifyNoMoreInteractions(this.productRepository);
    }
}