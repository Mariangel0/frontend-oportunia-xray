package com.frontend.oportunia.domain.model

import java.util.Date

data class Streak(
    val id: Long? = null,
    val days: Int,
    val lastActivity: Date,
    val bestStreak: Int,
    val student: Student,
)