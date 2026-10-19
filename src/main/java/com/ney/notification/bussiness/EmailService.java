package com.ney.notification.bussiness;

import com.ney.notification.bussiness.dto.TaskDto;
import com.ney.notification.bussiness.dto.TaskStatusDto;
import com.ney.notification.bussiness.enums.NotificationStatusEnum;
import com.ney.notification.infrastructure.exception.EmailException;
import com.ney.notification.infrastructure.messages.kafka.producer.KafkaProducerConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final KafkaProducerConfig kafkaProducerConfig;

    @Value("${email.sender.from}")
    public String from;

    @Value("${email.send.nameFrom}")
    public String nameFrom;

    public void sendEmail(TaskDto taskDto){
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setFrom(new InternetAddress(from, nameFrom));
            helper.setTo(InternetAddress.parse(taskDto.getCreatedBy()));
            helper.setSubject("Notification Task");
            Context context = new Context();
            context.setVariable("taskName", taskDto.getName());
            context.setVariable("scheduledAt", taskDto.getScheduledDate());
            context.setVariable("description", taskDto.getDescription());
            String content = templateEngine.process("notification", context);
            helper.setText(content, true);
            javaMailSender.send(message);

            kafkaProducerConfig.sendTaskStatus(taskDto.getId(), NotificationStatusEnum.NOTIFIED);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new EmailException("Erro ao enviar o Email, " + e.getCause());
        }
    }

    private TaskStatusDto buildStatus(String id, NotificationStatusEnum status){
        return TaskStatusDto.builder()
                    .id(id)
                    .notificationStatusEnum(status)
                .build();

    }
}
