package com.frontend.oportunia.domain.model

data class Streak(
    val id: Long,
    val studentId: Long,
    val days: Int,
    val lastActivity: String,
    val bestStreak: Int
)