package com.frontend.oportunia.data.remote.dto

data class StreakDto(
    val id: Long,
    val studentId: StudentDto,
    val days: Int,
    val lastActivity: String,
    val bestStreak: Int
)