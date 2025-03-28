package com.frontend.oportunia.domain.model

data class Notification(
    val id: Long,
    val userId: User,
    val type: String,
    val message: String,
    val readed: Boolean,
    val date: String
)
