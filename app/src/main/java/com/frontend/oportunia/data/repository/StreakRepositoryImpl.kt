package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.StreakDataSource
import com.frontend.oportunia.data.mapper.StreakMapper
import com.frontend.oportunia.domain.error.DomainError
import com.frontend.oportunia.domain.model.Ability
import com.frontend.oportunia.domain.model.Education
import com.frontend.oportunia.domain.model.Streak
import com.frontend.oportunia.domain.repository.StreakRepository
import kotlinx.coroutines.flow.first
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class StreakRepositoryImpl @Inject constructor (
    private val dataSource: StreakDataSource,
    private val streakMapper: StreakMapper
) : StreakRepository {

    companion object {
        private const val TAG = "StreakRepository"
    }

    override suspend fun createStreak(streak: Streak): Result<Streak> {
        val streakDto = streakMapper.mapToDto(streak)
        return try {
            val response = dataSource.createStreak(streakDto)
            if (response.isSuccessful && response.body() != null) {
                Result.success(streakMapper.mapToDomain(response.body()!!))
            } else {
                Result.failure(Exception("Error en la creación de la educación: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun findAllStreaks(): Result<List<Streak>> {
        return try {
            dataSource.getStreaks().map { streakDto ->
                streakMapper.mapToDomainList(streakDto)
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Cannot connect to server. Please check your internet connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching tasks: ${e.message}"))
        }
    }


    override suspend fun getStreak(studentId: Long): Result<Streak> = runCatching {
        val response = dataSource.getStreakByStudentId(studentId).getOrThrow()
        streakMapper.mapToDomain(response)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch streak for student $studentId", throwable)
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch streak")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping streak")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError("An unknown error occurred")
        }
    }

    override suspend fun findStreakById(streakId: Long): Result<Streak> =
        dataSource.getStreakById(streakId).map { reviewDto ->
            streakMapper.mapToDomain(reviewDto)
        }
}

