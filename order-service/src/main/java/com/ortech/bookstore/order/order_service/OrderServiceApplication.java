package com.ortech.bookstore.order.order_service;

import com.ortech.bookstore.order.order_service.config.ApplicationProperties;
import com.ortech.bookstore.order.order_service.config.RabbitMqConfig;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ConfigurationPropertiesScan(basePackageClasses = {RabbitMqConfig.class, ApplicationProperties.class})
@EnableSchedulerLock(defaultLockAtMostFor = "10m")
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
