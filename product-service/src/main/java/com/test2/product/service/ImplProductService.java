package com.test2.product.service;

import com.test2.product.entity.Product;
import com.test2.product.entity.Tariff;
import com.test2.product.payload.NewProduct;
import com.test2.product.payload.ProductDTO;
import com.test2.product.payload.UpdateProduct;
import com.test2.product.repository.ProductRepository;
import com.test2.product.service.kafka.KafkaSendService;
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
    private static final String TOPIC_PRODUCT = "topic.product";
    private final KafkaSendService<String, ProductDTO> kafkaSendService;
    private final ProductRepository productRepository;
    private final TariffService tariffService;
    private final ProductMapper productMapper;

    @Transactional
    @Override
    public ProductDTO createProduct(NewProduct newProduct) {
        Product product = this.productMapper.mapToProduct(newProduct);
        Tariff tariff = tariffService.getTariffById(newProduct.getTariffId())
                .orElseThrow(() -> new NoSuchElementException("product_service.tariff.errors.not.found"));
        product.setTariff(tariff);
        this.productRepository.save(product);
        ProductDTO productDTO = this.productMapper.mapToProductDTO(product);
        this.kafkaSendService.sendMessage(TOPIC_PRODUCT, productDTO.getId(), productDTO);
        return productDTO;
    }

    @Transactional
    @Override
    public void updateProduct(String id, UpdateProduct updateProduct) {
        UUID uuid = this.productMapper.mapToUUID(id);
        Tariff tariff = this.tariffService.getTariffById(updateProduct.getTariffId())
                .orElse(null);
        this.productRepository.findById(uuid)
                .ifPresentOrElse(product -> {
                    this.productMapper.mapToProduct(product, updateProduct, tariff);
                    product.increaseVersion();
                    this.kafkaSendService.sendMessage(TOPIC_PRODUCT, product.getId().toString(), productMapper.mapToProductDTO(product));
                }, () -> {
                    throw new NoSuchElementException("product_service.update.errors.not.found");
                });
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ProductDTO> findProductById(String id) {
        UUID uuid = this.productMapper.mapToUUID(id);
        return this.productRepository.findById(uuid)
                .map(productMapper::mapToProductDTO);
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
