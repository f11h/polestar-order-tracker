package io.f11h.polestarordertracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties
@EnableScheduling
public class PolestarOrderTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PolestarOrderTrackerApplication.class, args);
    }

}
