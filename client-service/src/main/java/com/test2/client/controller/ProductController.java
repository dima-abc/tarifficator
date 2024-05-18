package com.test2.client.controller;

import com.test2.client.payload.product.NewProduct;
import com.test2.client.payload.product.ProductDTO;
import com.test2.client.service.ProductClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/client/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductClientService productService;

    @PostMapping({"", "/"})
    public Mono<ResponseEntity<ProductDTO>> createProduct(@Valid @RequestBody NewProduct newProduct,
                                                          UriComponentsBuilder uriBuilder,
                                                          @RequestHeader("Authorization") String accessToken) {
        return this.productService.createProduct(newProduct, accessToken)
                .map(productDTO -> ResponseEntity
                        .created(uriBuilder.replacePath("/api/v1/client/products/{id}")
                                .build(productDTO.id()))
                        .body(productDTO));
    }

    @DeleteMapping("{productId}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable("productId") String productId,
                                                    @RequestHeader("Authorization") String accessToken) {
        return this.productService.deleteProduct(productId, accessToken)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
