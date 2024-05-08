package com.test2.product.controller;

import com.test2.product.enums.TypeProduct;
import com.test2.product.payload.NewProduct;
import com.test2.product.payload.ProductDTO;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductsControllerTest {
    @Mock
    ProductService productService;
    @InjectMocks
    ProductsController productsController;

    @Test
    void dependencyNotNull() {
        assertNotNull(this.productService);
        assertNotNull(this.productsController);
    }

    @Test
    void findAllProducts_returns_empty_list() {
        doReturn(List.of()).when(this.productService).findAllProducts();
        Iterable<ProductDTO> products = this.productsController.findAllProducts();
        assertFalse(products.iterator().hasNext());
    }

    @Test
    void findAllProducts_returns_Iterable_productDTO() {
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
        doReturn(List.of(productDTO)).when(this.productService).findAllProducts();
        Iterable<ProductDTO> products = this.productsController.findAllProducts();
        assertTrue(products.iterator().hasNext());
        assertEquals(productDTO, products.iterator().next());
    }

    @Test
    void createProduct_request_isValid_returns_CreatedProduct() throws BindException {
        UUID tariffId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        NewProduct newProduct = NewProduct.of().name("Product")
                .typeProductId(TypeProduct.LOAN.getId())
                .startDate("2020-04-04").endDate("2025-05-05")
                .description("detail product")
                .tariffId(tariffId.toString())
                .authorId(authorId.toString())
                .build();
        ProductDTO productDTO = ProductDTO.of().id(productId.toString()).name("Product")
                .typeProductId(TypeProduct.LOAN.getId())
                .typeProductName(TypeProduct.LOAN.getName())
                .startDate("2020-04-04").endDate("2025-05-05")
                .description("detail product")
                .tariffId(tariffId.toString())
                .tariffVersion(1L)
                .authorId(authorId.toString())
                .version(1L).build();
        BindingResult bindingResult = new BeanPropertyBindingResult(newProduct, "newProduct");
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString("http://localhost");
        doReturn(productDTO).when(this.productService).createProduct(newProduct);
        ResponseEntity<?> result = this.productsController
                .createProduct(newProduct, bindingResult, uriComponentsBuilder);
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(URI.create("http://localhost/api/v1/products/%s".formatted(productId.toString())),
                result.getHeaders().getLocation());
        assertEquals(productDTO, result.getBody());
        verify(this.productService).createProduct(newProduct);
        verifyNoMoreInteractions(this.productService);
    }

    @Test
    void createProduct_request_IsInvalid_returns_BadRequest() {
        UUID tariffId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        NewProduct newProduct = NewProduct.of().name(" ")
                .typeProductId(TypeProduct.LOAN.getId())
                .startDate("2020-04-04").endDate("2025-05-05")
                .description("detail product")
                .tariffId(tariffId.toString())
                .authorId(authorId.toString())
                .build();
        BindingResult bindingResult = new BeanPropertyBindingResult(newProduct, "newProduct");
        bindingResult.addError(new FieldError("newProduct", "name", "error"));
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString("http://localhost");
        BindException errors = assertThrows(BindException.class,
                () -> this.productsController.createProduct(newProduct, bindingResult, uriComponentsBuilder));
        assertEquals(new FieldError("newProduct", "name", "error"), errors.getFieldError());
        verifyNoInteractions(this.productService);
    }
}