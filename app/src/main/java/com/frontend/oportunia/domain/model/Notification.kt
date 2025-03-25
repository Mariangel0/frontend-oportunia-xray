package com.frontend.oportunia.domain.model

data class Notification(
    val id: Long,
    val studentId: Long,
    val type: String,
    val message: String,
    val readed: Boolean,
    val date: String
)
