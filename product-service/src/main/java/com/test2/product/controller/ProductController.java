package com.test2.product.controller;

import com.test2.product.payload.ProductDTO;
import com.test2.product.payload.UpdateProduct;
import com.test2.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/products/{uuid}")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final MessageSource messageSource;

    @ModelAttribute("product")
    public ProductDTO getProduct(@PathVariable("uuid") String uuid) {
        return this.productService.findProductById(uuid)
                .orElseThrow(() -> new NoSuchElementException("product_service.error.product.not_found"));
    }

    @GetMapping
    public ProductDTO findProduct(@ModelAttribute("product") ProductDTO product) {
        return product;
    }

    @PatchMapping
    public ResponseEntity<?> updateProduct(@PathVariable("uuid") String uuid,
                                           @RequestBody UpdateProduct updateProduct,
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

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception,
                                                                      Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        Objects.requireNonNull(this.messageSource.getMessage(exception.getMessage(), new Object[0],
                                exception.getMessage(), locale))));
    }
}
