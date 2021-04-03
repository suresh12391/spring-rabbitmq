package com.rabbitmq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQListener implements MessageListener {

    public void onMessage(Message message) {
       log.info("Consuming Message - " + new String(message.getBody()));
    }
}
