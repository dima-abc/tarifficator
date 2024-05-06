package com.test2.tariff.service.kafka;

import com.test2.tariff.payload.ProductDTO;
import com.test2.tariff.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProductReceiveService implements KafkaReceiveService<String, ProductDTO> {
    private final ProductService productService;
    private static final String TOPIC_PRODUCT = "topic.product";

    @KafkaListener(topics = TOPIC_PRODUCT)
    @Override
    public ProductDTO receive(ConsumerRecord<String, ProductDTO> record) {
        log.info("Receive topic: {}", record.topic());
        log.info("Receive key: {}", record.key());
        log.info("Receive value: {}", record.value());
        ProductDTO product = record.value();
        if (product != null) {
            productService.createProduct(product);
        }
        return product;
    }
}
