package com.test2.product.controller;

import com.test2.product.payload.ProductDTO;
import com.test2.product.payload.UpdateProduct;
import com.test2.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/products/{uuid}")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @ModelAttribute("product")
    public ProductDTO getProduct(@PathVariable("uuid") String uuid) {
        return this.productService.findProductById(uuid)
                .orElseThrow(() -> new NoSuchElementException("product_service.errors.not.found"));
    }

    @GetMapping
    public ProductDTO findProduct(@ModelAttribute("product") ProductDTO product) {
        return product;
    }

    @PatchMapping
    public ResponseEntity<?> updateProduct(@PathVariable("uuid") String uuid,
                                           @Valid @RequestBody UpdateProduct updateProduct,
                                           BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            this.productService.updateProduct(uuid, updateProduct);
            return ResponseEntity.noContent()
                    .build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@PathVariable("uuid") String uuid) {
        this.productService.deleteProductById(uuid);
        return ResponseEntity.noContent()
                .build();
    }
}
