package com.frontend.oportunia.data.datasource.model

data class StreakDto(
    val id: Long,
    val studentId: StudentDto,
    val days: Int,
    val lastActivity: String,
    val bestStreak: Int
)