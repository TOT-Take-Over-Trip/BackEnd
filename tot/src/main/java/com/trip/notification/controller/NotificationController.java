package com.trip.notification.controller;

import com.trip.notification.model.NotificationDto;
import com.trip.notification.service.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
@CrossOrigin("*")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping()
    public List<NotificationDto> getNotification(@RequestParam("memberId") int memberId){
        return notificationService.getNotifications(memberId);
    }

    @PostMapping()
    public void insertNotification(@RequestParam("memberId") int memberId, @RequestBody String content){
        notificationService.insertNotification(memberId, content);
    }

}
