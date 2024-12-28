package com.enzith.nexgen.service;

import com.enzith.nexgen.dto.response.NotificationResponse;
import com.enzith.nexgen.entity.Notification;

import java.util.Map;

public interface NotificationService {
    NotificationResponse createNotification(Notification notification);
    Map<String, Object> findAllNotifications(String status, Integer currentPage, Integer pageSize);

    NotificationResponse seenNotification(Long notificationId);
}
