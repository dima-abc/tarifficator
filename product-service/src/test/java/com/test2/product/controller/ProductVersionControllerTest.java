package com.test2.product.controller;

import com.test2.product.enums.TypeProduct;
import com.test2.product.payload.ProductDTO;
import com.test2.product.service.ProductVersionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ProductVersionControllerTest {
    @Mock
    private ProductVersionService productVersionService;
    @InjectMocks
    private ProductVersionController productVersionController;

    @Test
    void dependencyNotNull() {
        assertNotNull(this.productVersionService);
        assertNotNull(this.productVersionController);
    }

    @Test
    void findActualProductVersion_return_current_Product() {
        UUID tariffId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        ProductDTO productDTO = ProductDTO.of().id(productId.toString()).name("Product")
                .typeProductId(TypeProduct.LOAN.getId())
                .typeProductName(TypeProduct.LOAN.getName())
                .startDate("2020-04-04").endDate("2025-05-05")
                .description("detail product")
                .tariffId(tariffId.toString())
                .tariffVersion(1L)
                .authorId(authorId.toString())
                .version(1L).build();
        doReturn(Optional.of(productDTO)).when(this.productVersionService)
                .findCurrentVersionProductById(productId.toString());
        ProductDTO actual = this.productVersionController.findActualProductVersion(productId.toString());
        assertNotNull(actual);
        assertEquals(productDTO, actual);
    }


    @Test
    void findActualProductVersion_return_NoSuchElement_exception() {
        UUID productId = UUID.randomUUID();
        doReturn(Optional.empty()).when(this.productVersionService)
                .findCurrentVersionProductById(productId.toString());
        String errorMessage = "Продукт с таким ID не найден";
        assertThrows(NoSuchElementException.class,
                () -> this.productVersionController.findActualProductVersion(productId.toString()),
                errorMessage);
    }

    @Test
    void findPreviousProductVersion_return_List_ProductDto() {
        UUID tariffId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        ProductDTO productDTO = ProductDTO.of().id(productId.toString()).name("Product")
                .typeProductId(TypeProduct.LOAN.getId())
                .typeProductName(TypeProduct.LOAN.getName())
                .startDate("2020-04-04").endDate("2025-05-05")
                .description("detail product")
                .tariffId(tariffId.toString())
                .tariffVersion(1L)
                .authorId(authorId.toString())
                .version(1L).build();
        doReturn(List.of(productDTO)).when(this.productVersionService)
                .findPreviousVersionsProductById(productId.toString());
        List<ProductDTO> actual = this.productVersionController.findPreviousProductVersion(productId.toString());
        assertNotNull(actual);
        assertEquals(List.of(productDTO), actual);
    }

    @Test
    void findPeriodProductVersion_returnList_ProductDto_period() {
        UUID tariffId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        ProductDTO productDTO = ProductDTO.of().id(productId.toString()).name("Product")
                .typeProductId(TypeProduct.LOAN.getId())
                .typeProductName(TypeProduct.LOAN.getName())
                .startDate("2020-04-04").endDate("2025-05-05")
                .description("detail product")
                .tariffId(tariffId.toString())
                .tariffVersion(1L)
                .authorId(authorId.toString())
                .version(1L).build();
        String startPeriod = "2020-04-04 10:10:00";
        String endPeriod = "2020-04-04 10:10:10";
        doReturn(List.of(productDTO)).when(this.productVersionService)
                .findBetweenDateProductById(productId.toString(), startPeriod, endPeriod);
        List<ProductDTO> actual = this.productVersionController.findPeriodProductVersion(productId.toString(), startPeriod, endPeriod);
        assertNotNull(actual);
        assertEquals(List.of(productDTO), actual);
    }

    @Test
    void revertVersionProduct_return_ProductDTO() {
        UUID tariffId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        ProductDTO productDTO = ProductDTO.of().id(productId.toString()).name("Product")
                .typeProductId(TypeProduct.LOAN.getId())
                .typeProductName(TypeProduct.LOAN.getName())
                .startDate("2020-04-04").endDate("2025-05-05")
                .description("detail product")
                .tariffId(tariffId.toString())
                .tariffVersion(1L)
                .authorId(authorId.toString())
                .version(1L).build();
        doReturn(Optional.of(productDTO)).when(this.productVersionService)
                .revertProductBeforeVersion(productId.toString());
        ProductDTO actual = this.productVersionController.revertVersionProduct(productId.toString());
        assertNotNull(actual);
        assertEquals(productDTO, actual);
    }

    @Test
    void revertVersionProduct_return_NoSuchElement_exception() {
        UUID productId = UUID.randomUUID();
        doReturn(Optional.empty()).when(this.productVersionService)
                .revertProductBeforeVersion(productId.toString());
        String errorMessage = "Версия продукта не найдена";
        assertThrows(NoSuchElementException.class,
                () -> this.productVersionController.revertVersionProduct(productId.toString()),
                errorMessage);
    }
}