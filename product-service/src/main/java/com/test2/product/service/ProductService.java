package com.test2.product.service;

import com.test2.product.payload.NewProduct;
import com.test2.product.payload.ProductDTO;
import com.test2.product.payload.UpdateProduct;

import java.util.Optional;

public interface ProductService {
    ProductDTO createProduct(NewProduct newProduct);

    Optional<ProductDTO> findProductById(String id);

    void updateProduct(String id, UpdateProduct updateProduct);

    void deleteProductById(String id);

    Iterable<ProductDTO> findAllProducts();
}
