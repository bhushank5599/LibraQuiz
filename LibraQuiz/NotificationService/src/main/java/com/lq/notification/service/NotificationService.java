package com.lq.notification.service;

import com.lq.notification.dto.NotificationResponse;
import com.lq.notification.dto.SendNotificationRequest;

import java.util.List;

public interface NotificationService {
    NotificationResponse sendNotification(SendNotificationRequest request);
    List<NotificationResponse> getUserNotifications(Long userId);
}
