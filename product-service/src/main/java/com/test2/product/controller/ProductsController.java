package com.test2.product.controller;

import com.test2.product.payload.NewProduct;
import com.test2.product.payload.ProductDTO;
import com.test2.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductService productService;

    @GetMapping({"", "/"})
    public Iterable<ProductDTO> findAllProducts() {
        return this.productService.findAllProducts();
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody NewProduct newProduct,
                                                    BindingResult bindingResult,
                                                    UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            ProductDTO productDTO = this.productService.createProduct(newProduct);
            return ResponseEntity
                    .created(uriComponentsBuilder.replacePath("/api/v1/products/{uuid}")
                            .build(Map.of("uuid", productDTO.getId())))
                    .body(productDTO);
        }
    }
}
