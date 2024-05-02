package com.test2.product.service;

import com.test2.product.entity.Product;
import com.test2.product.payload.NewProduct;
import com.test2.product.payload.ProductDTO;
import com.test2.product.payload.UpdateProduct;
import com.test2.product.repository.ProductRepository;
import com.test2.product.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ImplProductService implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    @Override
    public ProductDTO createProduct(NewProduct newProduct) {
        Product product = this.productMapper.mapToProduct(newProduct);
        this.productRepository.save(product);
        return this.productMapper.mapToProductDTO(product);
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<ProductDTO> findProductById(String id) {
        UUID uuid = this.productMapper.mapToUUID(id);
        return this.productRepository.findById(uuid)
                .map(productMapper::mapToProductDTO);
    }

    @Transactional
    @Override
    public void updateProduct(String id, UpdateProduct updateProduct) {
        UUID uuid = this.productMapper.mapToUUID(id);
        this.productRepository.findById(uuid)
                .ifPresentOrElse(product -> {
                    this.productMapper.mapToProduct(product, updateProduct);
                    product.increaseVersion();
                }, () -> {
                    throw new NoSuchElementException("product_service.update.errors.not.found");
                });
    }

    @Override
    public void deleteProductById(String id) {
        UUID uuid = this.productMapper.mapToUUID(id);
        this.productRepository.deleteById(uuid);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<ProductDTO> findAllProducts() {
        Iterable<Product> products = this.productRepository.findAll();
        return StreamSupport.stream(products.spliterator(), false)
                .map(productMapper::mapToProductDTO)
                .toList();
    }
}
