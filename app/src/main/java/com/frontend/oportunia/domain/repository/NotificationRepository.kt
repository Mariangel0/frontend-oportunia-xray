package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.Notification

interface NotificationRepository {
    suspend fun findNotificationById(notificationId: Long): Result<Notification>
    suspend fun findAllNotifications(): Result<List<Notification>>
}