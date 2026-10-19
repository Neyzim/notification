package com.ney.notification.bussiness.dto;

import com.ney.notification.bussiness.enums.NotificationStatusEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskStatusDto {

    private String id;
    private NotificationStatusEnum notificationStatusEnum;

}
