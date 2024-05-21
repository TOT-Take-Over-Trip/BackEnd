package com.trip.notification.model.mapper;

import com.trip.notification.model.NotificationDto;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationMapper {

    List<NotificationDto> selectNotifications(int memberId);

    void insertNotification(HashMap<String, Object> map);

}
