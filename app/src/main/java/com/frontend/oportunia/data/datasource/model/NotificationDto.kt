package com.frontend.oportunia.data.datasource.model

data class NotificationDto(
    val id: Long,
    val studentId: Long,
    val type: String,
    val message: String,
    val readed: Boolean,
    val date: String
)
