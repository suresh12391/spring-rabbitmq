package com.rabbitmq.service;

import com.rabbitmq.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQSender {

    @Qualifier("rabbitTemplateBean")
    @Autowired
    private AmqpTemplate rabbitMqTemplate;

    @Value("${backend.rabbitmq.exchange}")
    private String exchange;

    @Value("${backend.rabbitmq.routingkey}")
    private String routingkey;

    public void convertAndSend(Category category) {
        rabbitMqTemplate.convertAndSend(exchange, routingkey, category);
    }

    public void convertAndSend(String message) {
        rabbitMqTemplate.convertAndSend(exchange, routingkey, message);
    }

    public void convertAndSend(String exchange, String routingKey, String messageData) {
        rabbitMqTemplate.convertAndSend(exchange, routingKey, messageData);
    }

    public void send(String exchange, String routingKey, Message messageObj) {
        rabbitMqTemplate.send(exchange, routingKey, messageObj);
    }
}
