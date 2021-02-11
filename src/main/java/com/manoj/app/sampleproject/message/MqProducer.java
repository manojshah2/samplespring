package com.manoj.app.sampleproject.message;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.manoj.app.sampleproject.message.request.BoardRequest;


@Configuration
public class MqProducer {

    @Autowired
    MqProperties mqProperties;
    @Autowired
    private RabbitTemplate rabbitTemplate;

   
    public void sendCommand(BoardRequest request,String boardId) {
        rabbitTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE_NAME, "inTopic", request);
    }

}
