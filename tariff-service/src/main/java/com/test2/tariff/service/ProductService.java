package com.test2.tariff.service;

import com.test2.tariff.entity.Product;
import com.test2.tariff.payload.ProductDTO;

public interface ProductService {
    Product createProduct(ProductDTO product);
}
