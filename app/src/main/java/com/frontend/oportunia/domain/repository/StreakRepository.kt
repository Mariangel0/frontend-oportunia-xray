package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.Education
import com.frontend.oportunia.domain.model.Streak

interface StreakRepository {
    suspend fun createStreak(streak: Streak): Result<Streak>
    suspend fun findAllStreaks(): Result<List<Streak>>
    suspend fun findStreakById(streakId: Long): Result<Streak>
    suspend fun getStreak(studentId: Long): Result<Streak>
}