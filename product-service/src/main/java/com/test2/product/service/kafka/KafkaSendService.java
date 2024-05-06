package com.test2.product.service.kafka;

public interface KafkaSendService<K, V> {

    /**
     * Отправить сообщение
     *
     * @param topic Queue messages
     * @param key   Key message
     * @param value Type message
     */
    public void sendMessage(String topic, K key, V value);
}
