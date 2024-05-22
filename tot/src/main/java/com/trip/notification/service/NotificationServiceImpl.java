package com.trip.notification.service;

import com.trip.notification.model.NotificationDto;
import com.trip.notification.model.mapper.NotificationMapper;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationMapper notificationMapper;
    @Override
    public List<NotificationDto> getNotifications(int memberId) {
        List<NotificationDto> notifications = notificationMapper.selectNotifications(memberId);
        Collections.sort(notifications, new Comparator<NotificationDto>() {
            @Override
            public int compare(NotificationDto o1, NotificationDto o2) {
                return o2.getNotificationId() - o1.getNotificationId();
            }
        });
        return notifications;
    }

    @Override
    public void insertNotification(int memberId, String content) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("content", content);
        notificationMapper.insertNotification(map);
    }

    @Override
    public void readNotification(int notificationId) {
        notificationMapper.readNotification(notificationId);
    }
}
