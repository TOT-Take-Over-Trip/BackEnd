package com.trip.notification.service;

import com.trip.notification.model.NotificationDto;
import com.trip.notification.model.mapper.NotificationMapper;
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
        return notificationMapper.selectNotifications(memberId);
    }

    @Override
    public void insertNotification(int memberId, String content) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("content", content);
        notificationMapper.insertNotification(map);
    }
}
