package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.datasource.model.NotificationDto

class NotificationMapper {
    fun mapToDomain(dto: NotificationDto): Notification = Notification(
        id = dto.id,
        studentId = dto.studentId,
        type = dto.type,
        message = dto.message,
        readed = dto.readed,
        date = dto.date
    )

    fun mapToDto(domain: Notification): NotificationDto = NotificationDto(
        id = domain.id,
        studentId = domain.studentId,
        type = domain.type,
        message = domain.message,
        readed = domain.readed,
        date = domain.date
    )
}