package com.ney.notification.controller;

import com.ney.notification.bussiness.EmailService;
import com.ney.notification.bussiness.dto.TaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> sendEmail(@RequestBody TaskDto taskDto){
        emailService.sendEmail(taskDto);

        return ResponseEntity.ok().build();
    }
}
