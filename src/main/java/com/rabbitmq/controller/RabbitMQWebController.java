package com.rabbitmq.controller;

import com.rabbitmq.model.Category;
import com.rabbitmq.service.RabbitMQSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/rabbitmq/")
@Slf4j
public class RabbitMQWebController {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    //direct
    //http://localhost:8080/api/rabbitmq/producer?code=001&description=MyDescription
    @GetMapping(value = "/producer")
    public String producer(@ModelAttribute Category category) {

        rabbitMQSender.convertAndSend(category);
        log.info("Send data to RabbitMq. Category {}, {}", category.getCode(), category.getDescription());

        return "Message sent to the RabbitMQ Successfully";
    }


    //http://localhost:8080/api/rabbitmq/exchange?exchangeName=topic-exchange&routingKey=queue.admin&messageData=MessageData
    @GetMapping(value = "/exchange")
    public String producer(@RequestParam("exchangeName") String exchange, @RequestParam("routingKey") String routingKey,
                           @RequestParam("messageData") String messageData) {

        rabbitMQSender.convertAndSend(exchange, routingKey, messageData);
        log.info("Send exchange data to mq with exchange: {}, routingKey: {} and msg: {}", exchange, routingKey, messageData);

        return "MessageData sent to the RabbitMQ exchange Successfully";
    }

    //http://localhost:8080/api/rabbitmq/fanout?exchangeName=fanout-exchange&messageData=message
    @GetMapping(value = "/fanout")
    public String fanout(@RequestParam("exchangeName") String exchange,
                         @RequestParam("messageData") String messageData) {

        rabbitMQSender.convertAndSend(exchange, "", messageData);
        log.info("Send exchange data to mq exchange:{}, msg: {}", exchange, messageData);

        return "Message sent to the RabbitMQ fanout Successfully";
    }

    //http://localhost:8080/api/rabbitmq/topic?exchangeName=topic-exchange&routingKey=queue.admin&messageData=MessageData
    @GetMapping(value = "/topic")
    public String producerTopic(@RequestParam("exchangeName") String exchange, @RequestParam("routingKey") String routingKey, @RequestParam("messageData") String messageData) {

        rabbitMQSender.convertAndSend(exchange, routingKey, messageData);
        log.info("Send to mq with topic type: {}, key: routingKey:{}, msg: {}", exchange, routingKey, messageData);

        return "Message sent to the RabbitMQ Topic Exchange Successfully";
    }


    //http://localhost:8080/api/rabbitmq/header?exchangeName=header-exchange&department=admin&messageData=MessageValue
    @GetMapping(value = "/header")
    public String producerHeader(@RequestParam("exchangeName") String exchange, @RequestParam("department") String department, @RequestParam("messageData") String messageData) {

        final MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("department", department);

        MessageConverter messageConverter = new SimpleMessageConverter();
        Message message = messageConverter.toMessage(messageData, messageProperties);

        rabbitMQSender.send(exchange, "", message);
        log.info("Send to mq with topic type: {}, key: department:{}, msg: {}", exchange, department, messageData);

        return "Message sent to the RabbitMQ Header Exchange Successfully";
    }

}
