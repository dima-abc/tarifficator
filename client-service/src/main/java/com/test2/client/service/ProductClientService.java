package com.test2.client.service;

import com.test2.client.payload.product.NewProduct;
import com.test2.client.payload.product.ProductDTO;
import reactor.core.publisher.Mono;

public interface ProductClientService {
    Mono<ProductDTO> createProduct(NewProduct newProduct, String token);

    Mono<Void> deleteProduct(String productId, String token);
}
