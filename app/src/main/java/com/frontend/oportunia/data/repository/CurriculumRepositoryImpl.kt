package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.CurriculumDataSource
import com.frontend.oportunia.data.mapper.CurriculumMapper
import com.frontend.oportunia.domain.error.DomainError
import com.frontend.oportunia.domain.model.Curriculum
import com.frontend.oportunia.domain.repository.CurriculumRepository
import kotlinx.coroutines.flow.first
import java.io.IOException

class CurriculumRepositoryImpl(
    private val dataSource: CurriculumDataSource,
    private val curriculumMapper: CurriculumMapper
) : CurriculumRepository {

    companion object {
        private const val TAG = "CurriculumRepository"
    }

    override suspend fun findAllCurriculums(): Result<List<Curriculum>> = runCatching {
        dataSource.getCurriculums().first().map { curriculumDto ->
            curriculumMapper.mapToDomain(curriculumDto)
        }
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch curriculums", throwable)

        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch curriculums")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping curriculums")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError("An unknown error occurred")
        }
    }

    override suspend fun findCurriculumById(curriculumId: Long): Result<Curriculum> = runCatching {
        val curriculumDto = dataSource.getCurriculumById(curriculumId) ?: throw DomainError.CurriculumError("Curriculum not found")
        curriculumMapper.mapToDomain(curriculumDto)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch curriculum with ID: $curriculumId", throwable)
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch curriculum")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping curriculum")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError("An unknown error occurred")
        }
    }
}