package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.EducationDataSource
import com.frontend.oportunia.data.mapper.EducationMapper
import com.frontend.oportunia.domain.error.DomainError
import com.frontend.oportunia.domain.model.Education
import com.frontend.oportunia.domain.repository.EducationRepository
import kotlinx.coroutines.flow.first
import java.io.IOException

class EducationRepositoryImpl(
    private val dataSource: EducationDataSource,
    private val educationMapper: EducationMapper
) : EducationRepository {

    companion object {
        private const val TAG = "EducationRepository"
    }

    override suspend fun findAllEducations(): Result<List<Education>> = runCatching {
        dataSource.getEducations().first().map { educationDto ->
            educationMapper.mapToDomain(educationDto)
        }
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch educations", throwable)

        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch educations")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping educations")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError("An unknown error occurred")
        }
    }

    override suspend fun findEducationById(educationId: Long): Result<Education> = runCatching {
        val educationDto = dataSource.getEducationById(educationId) ?: throw DomainError.EducationError("Education not found")
        educationMapper.mapToDomain(educationDto)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch education with ID: $educationId", throwable)
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch education")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping education")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError("An unknown error occurred")
        }
    }
}
