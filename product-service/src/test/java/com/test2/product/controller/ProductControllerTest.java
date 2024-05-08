package com.test2.product.controller;

import com.test2.product.enums.TypeProduct;
import com.test2.product.payload.ProductDTO;
import com.test2.product.payload.UpdateProduct;
import com.test2.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @Mock
    ProductService productService;

    @InjectMocks
    ProductController productController;

    @Test
    void dependencyNotNull() {
        assertNotNull(this.productService);
        assertNotNull(this.productController);
    }


    @Test
    void getProduct_returnProductDTO() {
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
        doReturn(Optional.of(productDTO)).when(this.productService)
                .findProductById(productId.toString());
        ProductDTO actual = this.productController.getProduct(productId.toString());
        assertEquals(productDTO, actual);
    }

    @Test
    void getProduct_return_NotSuchElement_Exception() {
        UUID productId = UUID.randomUUID();
        doReturn(Optional.empty()).when(this.productService)
                .findProductById(productId.toString());
        String errorMessage = "Продукт с таким ID не найден";
        assertThrows(NoSuchElementException.class,
                () -> this.productController.getProduct(productId.toString()),
                errorMessage);
    }

    @Test
    void findProduct_return_productDTO() {
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
        ProductDTO actual = this.productController.findProduct(productDTO);
        assertEquals(productDTO, actual);
    }

    @Test
    void updateProduct_request_valid_return_noContent() throws BindException {
        UUID tariffId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        UpdateProduct updateProduct = UpdateProduct.of().name("Product")
                .typeProductId(TypeProduct.LOAN.getId())
                .startDate("2020-04-04").endDate("2025-05-05")
                .description("detail product")
                .tariffId(tariffId.toString())
                .authorId(authorId.toString())
                .build();
        BindingResult bindingResult = new BeanPropertyBindingResult(updateProduct, "updateProduct");
        ResponseEntity<?> actual = this.productController.updateProduct(productId.toString(),
                updateProduct, bindingResult);
        assertNotNull(actual);
        assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
    }

    @Test
    void updateProduct_request_invalid_return_noContent() {
        UUID tariffId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        UpdateProduct updateProduct = UpdateProduct.of().name(" ")
                .typeProductId(TypeProduct.LOAN.getId())
                .startDate("2020-04-04").endDate("2025-05-05")
                .description("detail product")
                .tariffId(tariffId.toString())
                .authorId(authorId.toString())
                .build();
        BindingResult bindingResult = new BeanPropertyBindingResult(updateProduct, "updateProduct");
        bindingResult.addError(new FieldError("updateProduct", "name", "error"));
        BindException bindException = assertThrows(BindException.class,
                () -> this.productController.updateProduct(productId.toString(), updateProduct, bindingResult));
        assertEquals(new FieldError("updateProduct", "name", "error"), bindException.getFieldError());
    }

    @Test
    void deleteProduct_return_noContent() {
        UUID productId = UUID.randomUUID();
        ResponseEntity<?> actual = this.productController.deleteProduct(productId.toString());
        assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
    }
}