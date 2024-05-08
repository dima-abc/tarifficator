package com.test2.tariff;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
@Slf4j
public class TariffApplication {

    public static void main(String[] args) {
        var application = new SpringApplication(TariffApplication.class);
        application.addListeners(new ApplicationPidFileWriter("./tariff.pid"));
        application.run(args);
        log.info("GO TO: http://localhost:8082/swagger-ui/index.html");
    }

}
