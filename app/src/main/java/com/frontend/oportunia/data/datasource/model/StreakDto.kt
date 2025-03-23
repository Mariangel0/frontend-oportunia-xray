package com.frontend.oportunia.data.datasource.model

data class StreakDto(
    val id: Long,
    val studentId: Long,
    val days: Int,
    val lastActivity: String,
    val bestStreak: Int
)