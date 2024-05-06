package com.test2.tariff.service;

import com.test2.tariff.entity.Product;
import com.test2.tariff.entity.Tariff;
import com.test2.tariff.payload.ProductDTO;
import com.test2.tariff.repository.ProductRepository;
import com.test2.tariff.service.mapper.TariffMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImplProductService implements ProductService {
    private final ProductRepository productRepository;
    private final TariffMapper tariffMapper;

    @Transactional
    @Override
    public Product createProduct(ProductDTO product) {
        Tariff tariff = Tariff.of()
                .id(tariffMapper.mapToUUID(product.getTariffId()))
                .build();
        Product newProduct = new Product(tariffMapper.mapToUUID(product.getId()),
                tariff,
                product.getVersion());
        return productRepository.save(newProduct);
    }
}
