package com.frontend.oportunia.data.remote.dto

import java.util.Date

data class StreakDto(
    val id: Long?,
    val days: Int,
    val lastActivity: Date,
    val bestStreak: Int,
    val student: StudentDto,
)
