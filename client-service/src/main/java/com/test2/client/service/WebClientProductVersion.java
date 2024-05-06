package com.test2.client.service;

import com.test2.client.payload.product.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class WebClientProductVersion implements ProductVersionClientService {
    private final WebClient webClient;

    @Override
    public Mono<ProductDTO> findActualProductVersion(String id) {
        return null;
    }

    @Override
    public Flux<ProductDTO> findPreviousProductVersion(String id) {
        return null;
    }

    @Override
    public Flux<ProductDTO> findPeriodProductVersion(String id, String startDate, String endDate) {
        return null;
    }

    @Override
    public Mono<ProductDTO> revertVersionProduct(String id) {
        return null;
    }
}
