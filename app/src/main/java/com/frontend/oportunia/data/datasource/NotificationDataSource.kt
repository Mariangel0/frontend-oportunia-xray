package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.dto.NotificationDto
import kotlinx.coroutines.flow.Flow

interface NotificationDataSource {
    suspend fun getNotifications(): Flow<List<NotificationDto>>
    suspend fun getNotificationById(id: Long): NotificationDto?
    suspend fun insertNotification(notificationDto: NotificationDto)
    suspend fun updateNotification(notificationDto: NotificationDto)
    suspend fun deleteNotification(notificationDto: NotificationDto)

}