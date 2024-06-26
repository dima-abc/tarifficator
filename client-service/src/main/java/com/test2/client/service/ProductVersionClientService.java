package com.test2.client.service;

import com.test2.client.payload.product.ProductDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductVersionClientService {
    Mono<ProductDTO> findActualProductVersion(String id, String token);

    Flux<ProductDTO> findPreviousProductVersion(String id, String token);

    Flux<ProductDTO> findPeriodProductVersion(String id, String startPeriod, String endPeriod, String token);

    Mono<ProductDTO> revertVersionProduct(String id, String token);
}
