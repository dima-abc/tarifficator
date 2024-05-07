package com.test2.client.service;

import com.test2.client.payload.product.NewProduct;
import com.test2.client.payload.product.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class WebClientProduct implements ProductClientService {
    private static final String BASE_URL_PRODUCT = "api/v1/products";
    private static final String BASE_URL_PRODUCT_ID = "/api/v1/products/{id}";
    private final WebClient webClient;

    @Override
    public Mono<ProductDTO> createProduct(NewProduct newProduct) {
        return this.webClient
                .post()
                .uri(BASE_URL_PRODUCT)
                .bodyValue(newProduct)
                .retrieve()
                .bodyToMono(ProductDTO.class);
    }

    @Override
    public Mono<Void> deleteProduct(String productId) {
        return this.webClient
                .delete()
                .uri(BASE_URL_PRODUCT_ID, productId)
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}
