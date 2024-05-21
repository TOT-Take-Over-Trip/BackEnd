package com.trip.notification.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDto {

    private int notificationId;
    private int memberId;
    private String content;
    private boolean readOrNot;
    private String createdDate;
    private String updatedDate;
    private String status;

}
