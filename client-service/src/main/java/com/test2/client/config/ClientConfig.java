package com.test2.client.config;

import com.test2.client.service.WebClientProduct;
import com.test2.client.service.WebClientProductVersion;
import com.test2.client.service.WebClientTariff;
import com.test2.client.service.WebClientAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {
    @Bean
    @Scope("prototype")
    public WebClient.Builder clientServiceWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClientAccount webClientUser(
            @Value("${bank.user.service.uri:http://localhost:8081}") String userBaseUrl,
            WebClient.Builder clientServiceBuilder) {
        return new WebClientAccount(clientServiceBuilder
                .baseUrl(userBaseUrl)
                .build());
    }

    @Bean
    public WebClientTariff webClientTariff(
            @Value("${bank.tariff.service.uri:http://localhost:8082}") String tariffBaseUrl,
            WebClient.Builder clientServiceBuilder) {
        return new WebClientTariff(clientServiceBuilder
                .baseUrl(tariffBaseUrl)
                .build());
    }

    @Bean
    public WebClientProduct webClientProduct(
            @Value("${bank.product.service.uri:http://localhost:8083}") String productBaseUrl,
            WebClient.Builder clientServiceBuilder) {
        return new WebClientProduct(clientServiceBuilder
                .baseUrl(productBaseUrl)
                .build());
    }

    @Bean
    public WebClientProductVersion webClientProductVersion(
            @Value("${bank.product.service.uri:http://localhost:8083}") String productBaseUrl,
            WebClient.Builder clientServiceBuilder) {
        return new WebClientProductVersion(clientServiceBuilder
                .baseUrl(productBaseUrl)
                .build());
    }


}
