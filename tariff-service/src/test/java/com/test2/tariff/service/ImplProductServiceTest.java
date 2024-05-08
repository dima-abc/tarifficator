package com.test2.tariff.service;

import com.test2.tariff.entity.Product;
import com.test2.tariff.entity.Tariff;
import com.test2.tariff.payload.ProductDTO;
import com.test2.tariff.repository.ProductRepository;
import com.test2.tariff.service.mapper.TariffMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImplProductServiceTest {
    @Mock
    ProductRepository productRepository;

    @Spy
    TariffMapper tariffMapper = new TariffMapper();

    @InjectMocks
    ImplProductService implProductService;

    @Test
    void dependencyNotNull() {
        assertNotNull(productRepository);
        assertNotNull(tariffMapper);
        assertNotNull(implProductService);
    }

    @Test
    void createProduct() {
        UUID uuidProduct = UUID.randomUUID();
        UUID uuidTariff = UUID.randomUUID();
        Tariff tariff = Tariff.of()
                .id(uuidTariff)
                .build();
        Product expected = new Product(uuidProduct, tariff, 1);
        doReturn(expected).when(this.productRepository)
                .save(expected);
        ProductDTO productDTO = new ProductDTO(uuidProduct.toString(), uuidTariff.toString(), 1);
        Product result = this.implProductService.createProduct(productDTO);
        assertEquals(expected, result);
        verify(this.productRepository).save(expected);
        verifyNoMoreInteractions(this.productRepository);
    }
}