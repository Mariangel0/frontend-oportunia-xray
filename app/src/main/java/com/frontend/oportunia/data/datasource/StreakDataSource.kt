package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.datasource.model.StreakDto
import kotlinx.coroutines.flow.Flow

interface StreakDataSource {
    suspend fun getStreaks(): Flow<List<StreakDto>>
    suspend fun getStreakById(id: Long): StreakDto?
    suspend fun insertStreak(streakDto: StreakDto)
    suspend fun updateStreak(streakDto: StreakDto)
    suspend fun deleteStreak(streakDto: StreakDto)
}