package com.rabbitmq;

import com.util.RabbitMqTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class SpringRabbitmqApplicationTests {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.virtualhost}")
    private String virtualhost;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Test
    void contextLoads() {

        log.info("Testing Sample RabbitMQ Connection...");
        RabbitMqTest.rabbiMQCheck(new String[]{host, virtualhost, username, password});
        log.info("Test Completed...");

    }

}
