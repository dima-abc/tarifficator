package com.test2.tariff;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class TariffApplication {

    public static void main(String[] args) {
        SpringApplication.run(TariffApplication.class, args);
        log.info("GO TO: http://localhost:8082/api/v1/tariffs");
    }

}
