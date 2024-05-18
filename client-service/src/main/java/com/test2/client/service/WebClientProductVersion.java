package com.test2.client.service;

import com.test2.client.payload.product.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class WebClientProductVersion implements ProductVersionClientService {
    public static final String BASE_URL_PRODUCT_ACTUAL = "api/v1/products/version/{uuid}/actual";
    public static final String BASE_URL_PRODUCT_PREVIOUS = "api/v1/products/version/{uuid}/previous";
    public static final String BASE_URL_PRODUCT_PERIOD = "api/v1/products/version/{uuid}/period";
    public static final String BASE_URL_PRODUCT_REVERT = "api/v1/products/version/{uuid}/revert";
    private final WebClient webClient;

    @Override
    public Mono<ProductDTO> findActualProductVersion(String id, String token) {
        return this.webClient
                .get()
                .uri(BASE_URL_PRODUCT_ACTUAL, id)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(ProductDTO.class);
    }

    @Override
    public Flux<ProductDTO> findPreviousProductVersion(String id, String token) {
        return this.webClient
                .mutate()
                .build()
                .get()
                .uri(BASE_URL_PRODUCT_PREVIOUS, id)
                .header("Authorization", token)
                .retrieve()
                .bodyToFlux(ProductDTO.class);
    }

    @Override
    public Flux<ProductDTO> findPeriodProductVersion(String id, String startPeriod, String endPeriod, String token) {
        return this.webClient
                .mutate()
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder.path(BASE_URL_PRODUCT_PERIOD)
                        .queryParam("start-period", startPeriod)
                        .queryParam("end-period", endPeriod)
                        .build(id))
                .header("Authorization", token)
                .retrieve()
                .bodyToFlux(ProductDTO.class);
    }

    @Override
    public Mono<ProductDTO> revertVersionProduct(String id, String token) {
        return this.webClient
                .post()
                .uri(BASE_URL_PRODUCT_REVERT, id)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(ProductDTO.class);
    }
}
