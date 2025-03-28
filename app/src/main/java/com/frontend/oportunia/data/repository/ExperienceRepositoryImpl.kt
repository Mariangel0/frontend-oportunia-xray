package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.ExperienceDataSource
import com.frontend.oportunia.data.mapper.ExperienceMapper
import kotlinx.coroutines.flow.first
import java.io.IOException
import com.frontend.oportunia.domain.repository.ExperienceRepository
import com.frontend.oportunia.domain.model.Experience
import com.frontend.oportunia.domain.error.DomainError

class ExperienceRepositoryImpl(
    private val dataSource: ExperienceDataSource,
    private val experienceMapper: ExperienceMapper
) : ExperienceRepository {

    companion object {
        private const val TAG = "ExperienceRepository"
    }

    override suspend fun findAllExperiences(): Result<List<Experience>> = runCatching {
        dataSource.getExperiences().first().map { experienceDto ->
            experienceMapper.mapToDomain(experienceDto)
        }
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch experiences", throwable)

        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch experiences")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping experiences")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError("An unknown error occurred")
        }
    }

    override suspend fun findExperienceById(experienceId: Long): Result<Experience> = runCatching {
        val experienceDto = dataSource.getExperienceById(experienceId) ?: throw DomainError.ExperienceError("Experience not found")
        experienceMapper.mapToDomain(experienceDto)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch experience with ID: $experienceId", throwable)
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch experience")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping experience")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError("An unknown error occurred")
        }
    }
}
