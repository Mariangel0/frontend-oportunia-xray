package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.datasource.model.StreakDto
import com.frontend.oportunia.data.mapper.StreakMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StreakDataSourceImpl(
    private val streakMapper: StreakMapper
) : StreakDataSource {

    override suspend fun getStreaks(): Flow<List<StreakDto>> = flow {
        val streaks = StreakProvider.findAllStreaks()  // Simula la obtención de rachas
        emit(streaks.map { streakMapper.mapToDto(it) })
    }

    override suspend fun getStreakById(id: Long): StreakDto? = StreakProvider.findStreakById(id)?.let {
        streakMapper.mapToDto(it)
    }

    override suspend fun insertStreak(streakDto: StreakDto) {
    }

    override suspend fun updateStreak(streakDto: StreakDto) {
    }

    override suspend fun deleteStreak(streakDto: StreakDto) {
    }
}
