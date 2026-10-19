package com.ney.notification.infrastructure.messages.kafka.producer;

import com.ney.notification.bussiness.dto.TaskStatusDto;
import com.ney.notification.bussiness.enums.NotificationStatusEnum;
import com.ney.notification.infrastructure.exception.MessageException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    public void sendTaskStatus(
            String id,
            NotificationStatusEnum dto){
        try {
            String json = objectMapper.writeValueAsString(dto);
            kafkaTemplate.send("status-notification", json);
        }catch (JacksonException e){
            throw new MessageException("Error at Object serialization");
        }
    }

}
