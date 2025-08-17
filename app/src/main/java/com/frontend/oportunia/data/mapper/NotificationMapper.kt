package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.NotificationDto
import com.frontend.oportunia.domain.model.Notification

class NotificationMapper(
    private val userMapper: UserMapper
) {
    fun mapToDomain(dto: NotificationDto): Notification = Notification(
        id = dto.id,
        userId = userMapper.mapToDomain(dto.userId),
        type = dto.type,
        message = dto.message,
        readed = dto.readed,
        date = dto.date
    )

    fun mapToDto(domain: Notification): NotificationDto = NotificationDto(
        id = domain.id,
        userId = userMapper.mapToDto(domain.userId),
        type = domain.type,
        message = domain.message,
        readed = domain.readed,
        date = domain.date
    )
}