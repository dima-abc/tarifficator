package com.test2.client.service;

import com.test2.client.payload.product.NewProduct;
import com.test2.client.payload.product.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class WebClientProduct implements ProductClientService {
    private final WebClient webClient;

    @Override
    public Mono<ProductDTO> createProduct(NewProduct newProduct) {
        return null;
    }

    @Override
    public Mono<Void> deleteProduct(String productId) {
        return null;
    }
}
