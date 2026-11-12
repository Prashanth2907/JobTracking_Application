package com.jobtrack.service;

import com.jobtrack.dto.response.NotificationDto;
import com.jobtrack.entity.User;

import java.util.List;

public interface NotificationService {
    void sendNotification(User user, String title, String message, String type, String linkUrl);
    List<NotificationDto> getUserNotifications(Long userId);
    long getUnreadCount(Long userId);
    void markAsRead(Long notificationId, Long userId);
    void markAllAsRead(Long userId);
}
