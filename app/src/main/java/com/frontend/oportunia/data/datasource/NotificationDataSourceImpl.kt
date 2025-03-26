package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.datasource.model.NotificationDto
import com.frontend.oportunia.data.mapper.NotificationMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NotificationDataSourceImpl(
    private val notificationMapper: NotificationMapper
) : NotificationDataSource {

    override suspend fun getNotifications(): Flow<List<NotificationDto>> = flow {
        val notifications = NotificationProvider.findAllNotifications()  // Simula la obtención de notificaciones
        emit(notifications.map { notificationMapper.mapToDto(it) })
    }

    override suspend fun getNotificationById(id: Long): NotificationDto? = NotificationProvider.findNotificationById(id)?.let {
        notificationMapper.mapToDto(it)
    }

    override suspend fun insertNotification(notificationDto: NotificationDto) {
    }

    override suspend fun updateNotification(notificationDto: NotificationDto) {
    }

    override suspend fun deleteNotification(notificationDto: NotificationDto) {
    }
}
