package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.NotificationDataSource
import com.frontend.oportunia.data.mapper.NotificationMapper
import kotlinx.coroutines.flow.first
import java.io.IOException

class NotificationRepositoryImpl(
    private val dataSource: NotificationDataSource,
    private val notificationMapper: NotificationMapper
) : NotificationRepository {

    companion object {
        private const val TAG = "NotificationRepository"
    }

    override suspend fun findAllNotifications(): Result<List<Notification>> = runCatching {
        dataSource.getNotifications().first().map { notificationDto ->
            notificationMapper.mapToDomain(notificationDto)
        }
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch notifications", throwable)

        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch notifications")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping notifications")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun findNotificationById(notificationId: Long): Result<Notification> = runCatching {
        val notificationDto = dataSource.getNotificationById(notificationId) ?: throw DomainError.NotificationError("Notification not found")
        notificationMapper.mapToDomain(notificationDto)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch notification with ID: $notificationId", throwable)
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch notification")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping notification")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }
}
