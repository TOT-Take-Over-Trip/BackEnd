package com.trip.notification.service;

import com.trip.notification.model.NotificationDto;
import java.util.List;

public interface NotificationService {

    List<NotificationDto> getNotifications(int memberId);

    void insertNotification(int memberId, String content);

    void readNotification(int notificationId);
}
