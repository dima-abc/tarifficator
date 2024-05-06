package com.test2.product.service.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface KafkaReceiveService<K, T> {
    /**
     * Получение сообщения от сервиса.
     *
     * @param record Тело сообщения
     * @return T type Object
     */
    public T receive(ConsumerRecord<K, T> record);
}
