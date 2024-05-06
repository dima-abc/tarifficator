package com.test2.client.service;

import com.test2.client.payload.product.ProductDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductVersionClientService {
    Mono<ProductDTO> findActualProductVersion(String id);

    Flux<ProductDTO> findPreviousProductVersion(String id);

    Flux<ProductDTO> findPeriodProductVersion(String id, String startDate, String endDate);

    Mono<ProductDTO> revertVersionProduct(String id);
}
