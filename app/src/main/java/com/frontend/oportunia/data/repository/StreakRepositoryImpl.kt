package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.StreakDataSource
import com.frontend.oportunia.data.mapper.StreakMapper
import com.frontend.oportunia.domain.error.DomainError
import com.frontend.oportunia.domain.model.Streak
import com.frontend.oportunia.domain.repository.StreakRepository
import kotlinx.coroutines.flow.first
import java.io.IOException

class StreakRepositoryImpl(
    private val dataSource: StreakDataSource,
    private val streakMapper: StreakMapper
) : StreakRepository {

    companion object {
        private const val TAG = "StreakRepository"
    }

    override suspend fun findAllStreaks(): Result<List<Streak>> = runCatching {
        dataSource.getStreaks().first().map { streakDto ->
            streakMapper.mapToDomain(streakDto)
        }
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch streaks", throwable)

        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch streaks")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping streaks")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError("An unknown error occurred")
        }
    }

    override suspend fun findStreakById(streakId: Long): Result<Streak> = runCatching {
        val streakDto = dataSource.getStreakById(streakId) ?: throw DomainError.StreakError("Streak not found")
        streakMapper.mapToDomain(streakDto)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch streak with ID: $streakId", throwable)
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch streak")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping streak")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError("An unknown error occurred")
        }
    }
}
