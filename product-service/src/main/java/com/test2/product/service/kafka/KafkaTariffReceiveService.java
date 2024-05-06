package com.test2.product.service.kafka;

import com.test2.product.payload.TariffDTO;
import com.test2.product.service.TariffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaTariffReceiveService implements KafkaReceiveService<String, TariffDTO> {
    private final TariffService tariffService;
    private static final String TOPIC_TARIFF = "topic.tariff";

    @KafkaListener(topics = TOPIC_TARIFF)
    @Override
    public TariffDTO receive(ConsumerRecord<String, TariffDTO> record) {
        log.info("Received topic: {}", record.topic());
        log.info("Receive key: {}", record.key());
        log.info("Receive value: {}", record.value());
        TariffDTO tariffDTO = record.value();
        if (tariffDTO != null) {
            return tariffService.createTariff(tariffDTO);
        }
        return tariffDTO;
    }
}
