package com.ney.notification.infrastructure.messages.rabbitMQ.consumer;

import com.ney.notification.bussiness.EmailService;
import com.ney.notification.bussiness.dto.TaskDto;
import com.ney.notification.infrastructure.messages.rabbitMQ.config.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = RabbitMqConfig.FILA_EMAIL)
    public void sendEmail(TaskDto dto){
        emailService.sendEmail(dto);
    }



}
