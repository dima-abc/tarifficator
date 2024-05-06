package com.test2.tariff.service.kafka;

import com.test2.tariff.payload.TariffDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaTariffSendService implements KafkaSendService<String, TariffDTO> {
    private final KafkaTemplate<String, TariffDTO> kafkaTemplate;

    @Override
    public void sendMessage(String topic, String key, TariffDTO value) {
        CompletableFuture<SendResult<String, TariffDTO>> future = kafkaTemplate.send(topic, key, value);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("FAILURE message, key: {}, type: {}, error: {}", key, value, ex.getMessage());

            } else {
                log.debug("SUCCESS message, key: {}, type: {}", key, value);
            }
        });
        kafkaTemplate.flush();
    }
}
