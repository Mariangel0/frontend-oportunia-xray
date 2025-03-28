package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.Streak

interface StreakRepository {
    suspend fun findAllStreaks(): Result<List<Streak>>
    suspend fun findStreakById(streakId: Long): Result<Streak>
}